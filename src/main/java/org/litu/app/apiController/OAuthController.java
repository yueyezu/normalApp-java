package org.litu.app.apiController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.apiController.vo.AccessTokenVo;
import org.litu.app.entity.SysAccesstoken;
import org.litu.app.entity.SysUser;
import org.litu.app.service.ISysAccesstokenService;
import org.litu.app.vo.LoginUserMsg;
import org.litu.base.controller.BaseApiController;
import org.litu.base.service.IBaseLogService;
import org.litu.base.service.ILoginService;
import org.litu.base.vo.ApiRes;
import org.litu.core.enums.ErrorEnum;
import org.litu.core.exception.LtServerException;
import org.litu.util.net.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.litu.base.vo.ApiRes.error;

/**
 * 系统授权的接口类，只适合手机端以及第三方授权使用。
 *
 * @author yueye
 */
@RestController
@RequestMapping("/api/oauth")
@Api(value = "第三方授权相关的操作", tags = {"第三方授权相关的操作"}, protocols = "http,https")
public class OAuthController extends BaseApiController {

    @Autowired
    ILoginService loginService;
    @Autowired
    private IBaseLogService optLogService;
    @Autowired
    private ISysAccesstokenService tokenService;

    /**
     * 获取授权码，——password模式 需要将用户设备的机器码发过来，用于认证使用。
     *
     * @param grant_type    授权类型，固定为password
     * @param client_id     客户端ID，识别客户端类型使用
     * @param client_secret 客户端密码，防止恶意客户端伪造
     * @param username      当前用户的用户名
     * @param password      当前用户的密码,客户端需要md5加密后再传
     * @param m_code        用户机器码，可以直接用mac
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ApiOperation(value = "获取授权码接口", notes = "密码模式，用户通过用户名和密码，获取授权码内容", httpMethod = "POST", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", value = "授权类型", paramType = "query", required = true, defaultValue = "password", dataType = "string"),
            @ApiImplicitParam(name = "client_id", value = "客户端类型", paramType = "query", required = true, defaultValue = "testclent", dataType = "string"),
            @ApiImplicitParam(name = "client_secret", value = "客户端密码", paramType = "query", required = false, defaultValue = "123456", dataType = "string"),
            @ApiImplicitParam(name = "username", value = "用户帐号", paramType = "query", required = true, defaultValue = "test", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "用户密码,需MD5加密", paramType = "query", required = true, defaultValue = "123456", dataType = "string"),
            @ApiImplicitParam(name = "m_code", value = "用户机器码，可以为mac", paramType = "query", required = true, defaultValue = "123", dataType = "string")
    })
    public ApiRes<AccessTokenVo> token(String grant_type, String client_id, String client_secret, String username, String password, String m_code) {
        if (StringUtils.isAnyBlank(grant_type, client_id, username, password, m_code)) {
            return error(ErrorEnum.ParamError, "输入参数必填项存在空值，请检查。");
        }

        String ip = NetUtil.getIp(request);
        String mac = NetUtil.getMac(ip);

        // 这里对授权的客户端ID和密码进行验证。
        boolean hasClient = tokenService.checkClient(client_id, client_secret);
        if (!hasClient) {
            return error(ErrorEnum.ClientError, "客户端ID和密码错误，请联系系统管理员。");
        }

        try {
            // 检测当前登录用户的信息是否正确
            SysUser user = loginService.checkLogin(client_id, username, password);
            if (user == null) {
                return ApiRes.error(ErrorEnum.UserPwdError);
            }

            // 组建授权Token对象信息
            SysAccesstoken accessToken = new SysAccesstoken();
            accessToken.setClientType(client_id);
            accessToken.setUserId(user.getId());
            accessToken.setClientMcode(m_code);
            accessToken.setClientIp(ip);
            accessToken.setClientMac(mac);
            accessToken = tokenService.createToken(accessToken);

            // 记录登录日志(异步)
            optLogService.setLogs("用户模块", "授权登录", "第三方授权登录，获取授权码", ip, user.getId(), client_id).addOptLogsRunnable();

            AccessTokenVo accessTokenVo = new AccessTokenVo();
            accessTokenVo.setAccess_token(accessToken.getToken());
            accessTokenVo.setRefresh_token(accessToken.getRefreshToken());
            accessTokenVo.setExpires_in(accessToken.getEnableTime());

            return ApiRes.ok(accessTokenVo);
        } catch (LtServerException se) {
            return ApiRes.error(se.getErrorMsg(), se.getMessage());
        } catch (Exception e) {
            return ApiRes.error(ErrorEnum.GetTokenError);
        }
    }

    /**
     * 根据刷新码，刷新获取授权码信息。
     *
     * @param grant_type
     * @param client_id
     * @param client_secret
     * @param refresh_token
     * @return
     */
    @RequestMapping(value = "/refreshtoken", method = RequestMethod.POST)
    @ApiOperation(value = "刷新授权码接口", notes = "密码模式，用户通过刷新码，重新获取授权码", httpMethod = "POST", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", value = "授权类型", paramType = "query", required = true, defaultValue = "password", dataType = "string"),
            @ApiImplicitParam(name = "client_id", value = "客户端类型", paramType = "query", required = true, defaultValue = "testclent", dataType = "string"),
            @ApiImplicitParam(name = "client_secret", value = "客户端密码", paramType = "query", required = false, defaultValue = "123456", dataType = "string"),
            @ApiImplicitParam(name = "refresh_token", value = "获取授权时的刷新码", paramType = "query", required = true, defaultValue = "", dataType = "string")
    })
    public ApiRes<AccessTokenVo> refreshToken(String grant_type, String client_id, String client_secret, String refresh_token) {
        if (StringUtils.isAnyBlank(grant_type, client_id, refresh_token)) {
            return ApiRes.error(ErrorEnum.ParamError, "输入参数必填项存在空值，请检查");
        }

        // 这里对授权的客户端ID和密码进行验证。
        boolean hasClient = tokenService.checkClient(client_id, client_secret);
        if (!hasClient) {
            return ApiRes.error(ErrorEnum.ClientError, "客户端ID和密码错误，请联系系统管理员。");
        }
        SysAccesstoken accessToken = tokenService.refreshToken(client_id, refresh_token);

        AccessTokenVo accessTokenVo = new AccessTokenVo();
        accessTokenVo.setAccess_token(accessToken.getToken());
        accessTokenVo.setRefresh_token(accessToken.getRefreshToken());
        accessTokenVo.setExpires_in(accessToken.getEnableTime());

        return ApiRes.ok(accessTokenVo);
    }

    /**
     * 验证授权码是否正确的接口，用于用户再次登录，根据授权码直接拿到用户信息
     *
     * @param grant_type
     * @param client_id
     * @param client_secret
     * @param token
     * @param m_code
     * @return
     */
    @RequestMapping(value = "/checktoken", method = RequestMethod.POST)
    @ApiOperation(value = "验证授权码接口", notes = "用户根据授权码验证登陆信息，获取登录的必要信息", httpMethod = "POST", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", value = "授权类型", paramType = "query", required = true, defaultValue = "password", dataType = "string"),
            @ApiImplicitParam(name = "client_id", value = "客户端类型", paramType = "query", required = true, defaultValue = "testclent", dataType = "string"),
            @ApiImplicitParam(name = "client_secret", value = "客户端密码", paramType = "query", required = false, defaultValue = "123456", dataType = "string"),
            @ApiImplicitParam(name = "token", value = "获取的授权码", paramType = "query", required = true, defaultValue = "", dataType = "string"),
            @ApiImplicitParam(name = "m_code", value = "用户机器码，可以为mac", paramType = "query", required = true, defaultValue = "123", dataType = "string")
    })
    public ApiRes<LoginUserMsg> checkToken(String grant_type, String client_id, String client_secret, String token, String m_code) {
        if (StringUtils.isAnyBlank(grant_type, client_id, token)) {
            return ApiRes.error(ErrorEnum.ParamError, "输入参数必填项存在空值，请检查");
        }

        // 这里对授权的客户端ID和密码进行验证。
        boolean hasClient = tokenService.checkClient(client_id, client_secret);
        if (!hasClient) {
            return ApiRes.error(ErrorEnum.ClientError, "客户端ID和密码错误，请联系系统管理员。");
        }

        SysAccesstoken accesstoken = tokenService.checkToken(client_id, token, m_code);
        // 授权码未获取到。
        if (accesstoken == null) {
            return ApiRes.error(ErrorEnum.TokenError, "授权码错误,请重新获取。");
        }
        // 授权码超时的错误
        if (accesstoken.getEnableFlag() == 0) {
            return ApiRes.error(ErrorEnum.TokenTimeout, "授权码已超时，请刷新或重新获取。");
        }

        // 记录登录日志(异步)
        String ip = NetUtil.getIp(request);
        optLogService.setLogs("用户模块", "授权登录", "第三方授权登录，验证码登录", ip, accesstoken.getUserId(), client_id).addOptLogsRunnable();

        LoginUserMsg userMsg = loginService.getLoginUserMsg(accesstoken.getUserId(), client_id);
        return ApiRes.ok(userMsg);
    }
}
