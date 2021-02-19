package org.litu.app.apiController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.SysUser;
import org.litu.app.vo.LoginUserMsg;
import org.litu.base.controller.BaseApiController;
import org.litu.base.service.IBaseLogService;
import org.litu.base.service.ILoginService;
import org.litu.base.vo.ApiRes;
import org.litu.core.enums.ErrorEnum;
import org.litu.core.exception.LtServerException;
import org.litu.core.login.LoginTokenUtil;
import org.litu.util.net.NetUtil;
import org.litu.util.web.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * 系统授权的接口类，适用于Web端系统，单点登录使用。
 * <p>
 * 同一域下，可以直接使用cookie管理logintoken信息，不同域下，需要第三方自主管理logintoken信息。
 *
 * @author yueye
 */
@RestController
@RequestMapping("/api/sso")
@Api(value = "单点登录相关的操作", tags = {"单点登录相关的操作,前台集成js：根目录/static/js/sso.js"}, protocols = "http,https")
public class SsoController extends BaseApiController {

    @Autowired
    private ILoginService loginService;
    @Autowired
    private IBaseLogService optLogService;

    /**
     * 用户第三方登录使用， 通过用户名密码登录，并获取到单点登录的token信息。
     * <p>
     * token信息只是作为单点登录的凭证，具体登录控制需要结合sso.js文件进行。 sso.js位置在： /static/js/sso.js
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "单点授权登录用端口", notes = "用户请求，根据用户名密码获取登录的token信息", httpMethod = "GET", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "systemCode", value = "系统编号", paramType = "query", required = true, defaultValue = "testSystem", dataType = "string"),
            @ApiImplicitParam(name = "account", value = "用户账号", paramType = "query", required = true, defaultValue = "test", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "用户密码，md5加密", paramType = "query", required = true, defaultValue = "123456", dataType = "string")})
    public ApiRes<String> login(String systemCode, String account, String password) {
        // 跨域请求需要的头说明
        // 允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
        response.addHeader("Access-Control-Allow-Origin", "*");// 允许所有来源访问
        response.addHeader("Access-Control-Allow-Method", "POST,GET");// 允许访问的方式

        if (StringUtils.isAnyBlank(account, password, systemCode)) {
            return ApiRes.error(ErrorEnum.ParamError, "系统编号、用户名或密码不能为空!");
        }
        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ErrorEnum.InvalidRequest, "当前系统不受单点登录系统管理!");
        }

        ApiRes result = null;
        try {
            // 获取验证登录信息以及获取用户信息
            SysUser user = loginService.checkLogin(systemCode, account, password);
            if (user == null) {
                return ApiRes.error(ErrorEnum.UserPwdError);
            }

            String ip = NetUtil.getIp(request);

            // 登录在cookie的顶级加入登录的token信息。 这里在跨域的情况下，很难获取到，所以就需要第三方系统自行处理了。
            String loginToken = LoginTokenUtil.getSSOToken(user.getId(), account, ip);
            CookieUtil.addCookie(response, SysContant.SSO_COOKIE_KEY, loginToken, 240, "/");

            // 记录登录日志(异步)
            optLogService.setLogs("用户模块", "单点登录", "首次单点登录", ip, user.getId(), systemCode).addOptLogsRunnable();

            // LoginUserMsg userMsg = loginService.getSsoUserMsg(user, systemCode);
            result = ApiRes.ok(loginToken);
        } catch (LtServerException se) {
            result = ApiRes.error(se.getErrorMsg(), se.getMessage());
        } catch (Exception e) {
            result = ApiRes.error(ErrorEnum.UserPwdError, "用户名或密码错误!");
        }
        return result;
    }

    /**
     * 用户第三方登录使用， 当认证成功，则直接返回用户的信息以及权限信息。
     * <p>
     * 第三方需要做对应的后台登录接口，然后通过后台调用该接口，获取登录成功信息。
     * <p>
     * 也可以前台进行调用，获取到成功信息后，在第三方后台再注册登录成功信息。这样存在一定的数据安全性问题。
     *
     * @return
     */
    @RequestMapping(value = "/loginToken", method = RequestMethod.POST)
    @ApiOperation(value = "单点授权登录用端口", notes = "用户请求，根据单点登录token信息进行登录,建议第三方服务端调用", httpMethod = "POST", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "systemCode", value = "系统编号", paramType = "query", required = true, defaultValue = "testSystem", dataType = "string"),
            @ApiImplicitParam(name = "loginToken", value = "用户登录后返回的loginToken信息", paramType = "query", required = true, defaultValue = "YWRtaW4wLWExMjM0NTY=", dataType = "string")})
    public ApiRes<LoginUserMsg> loginToken(String systemCode, String loginToken) {
        if (StringUtils.isAnyBlank(systemCode, loginToken)) {
            return ApiRes.error(ErrorEnum.ParamError, "系统编号和登录token信息不能为空!");
        }

        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ErrorEnum.InvalidRequest, "当前系统不受单点登录系统管理!");
        }

        try {
            String ip = NetUtil.getIp(request);
            String account = LoginTokenUtil.checkSSOToken(loginToken, ip);

            // 认证成功，直接返回给前台用户的信息。
            SysUser user = loginService.getUserByAccount(account);
            if (user == null) {
                CookieUtil.deleteCookie(request, response, "");
                return ApiRes.error(ErrorEnum.UserPwdError, "当前登录用户信息错误!");
            }

            // 记录登录日志(异步)
            optLogService.setLogs("用户模块", "单点登录", "再次登录", ip, user.getId(), systemCode).addOptLogsRunnable();

            LoginUserMsg userMsg = loginService.getSsoUserMsg(user, systemCode);
            return ApiRes.ok(userMsg);
        } catch (LtServerException lex) {
            return ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            return ApiRes.error(ErrorEnum.ServerError, "登录失败！");
        }
    }

    /**
     * 用户第三方登录后注销登录使用
     *
     * @param systemCode
     * @param loginToken
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "注销登录用端口", notes = "用户请求，根据单点登录token信息进行注销操作", httpMethod = "POST", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "systemCode", value = "系统编号", paramType = "query", required = true, defaultValue = "testSystem", dataType = "string"),
            @ApiImplicitParam(name = "loginToken", value = "用户登录后返回的loginToken信息", paramType = "query", required = true, defaultValue = "YWRtaW4wLWExMjM0NTY=", dataType = "string")})
    public ApiRes<String> logout(String systemCode, String loginToken) {
        // 跨域请求需要的头说明
        // 允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
        response.addHeader("Access-Control-Allow-Origin", "*");// 允许所有来源访问
        response.addHeader("Access-Control-Allow-Method", "POST,GET");// 允许访问的方式

        if (StringUtils.isAnyBlank(systemCode, loginToken)) {
            return ApiRes.error(ErrorEnum.ParamError, "系统编号和登录token信息不能为空!");
        }

        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ErrorEnum.InvalidRequest, "当前系统不受单点登录系统管理!");
        }

        ApiRes result = null;
        try {
            String userId = LoginTokenUtil.removeSSOToken(loginToken);

            // 记录登录日志(异步)
            String ip = NetUtil.getIp(request);
            optLogService.setLogs("用户模块", "单点登录", "注销登录", ip, userId, systemCode).addOptLogsRunnable();

            result = ApiRes.ok("注销成功！");
        } catch (LtServerException lex) {
            result = ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            result = ApiRes.error(ErrorEnum.ServerError, "注销失败！");
        }

        return result;
    }

    /*----------------- 以上为单点登录用方法,以下为扫码登录用方法  ---------------------*/

    /**
     * 获取扫描使用登录的二维码图片
     * <p>
     * 第三方使用直接调用
     *
     * @author yueye
     */
    @RequestMapping(value = "/qrCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取扫码登录用的二维码的方法", notes = "直接以文件流的方式，返回登录用二维码图片，第三方前端可直接掉用", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "systemCode", value = "系统编号", paramType = "query", required = true, defaultValue = "testSystem", dataType = "string")})
    public ApiRes<String> qrCode(String systemCode) {
        if (StringUtils.isBlank(systemCode)) {
            return ApiRes.error(ErrorEnum.ParamError, "系统编号参数不能为空！");
        }
        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ErrorEnum.InvalidRequest, "当前系统不受单点登录系统管理!");
        }

        try {
            String ip = NetUtil.getIp(request);
            ByteArrayOutputStream qrCodeOut = LoginTokenUtil.getQrCode(ip, systemCode);

            // 通知浏览器不要缓存
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(qrCodeOut.toByteArray());

            outputStream.flush();
            qrCodeOut.close();
            outputStream.close(); // 输出到web页面
        } catch (LtServerException lex) {
            return ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiRes.error(ErrorEnum.ServerError, "二维码生成失败！");
        }
        return null;
    }

    /**
     * 第三方移动端扫描后，认证二维码的方法，认证成功后，在这里缓存认证成功的帐号信息，供二维码登录放检测使用。
     *
     * @param qrCode
     * @param account
     * @return
     * @author yueye
     */
    @RequestMapping(value = "/checkQrCode", method = RequestMethod.POST)
    @ApiOperation(value = "第三方扫码认证的方法", notes = "通过扫描二维码获取的二维码信息，认证当前二维码登录的情况以及传递登录用户信息，第三方相关App直接掉用", httpMethod = "POST", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "qrCode", value = "二维码信息", paramType = "query", required = true, defaultValue = "123212312312", dataType = "string"),
            @ApiImplicitParam(name = "account", value = "扫码的帐号信息", paramType = "query", required = true, defaultValue = "admin", dataType = "string")})
    public ApiRes<String> checkQrCode(String qrCode, String account) {
        if (StringUtils.isAnyBlank(qrCode, account)) {
            return ApiRes.error(ErrorEnum.ParamError, "二维码和用户账号信息不能为空！");
        }

        try {
            // 认证成功，直接返回给前台用户的信息。
            SysUser user = loginService.getUserByAccount(account);
            if (user == null) {
                ApiRes.error(ErrorEnum.UserPwdError, "授权服务器，未找到当前用户信息。");
            }

            boolean result = LoginTokenUtil.checkQrCode(qrCode, account);

            return !result ? ApiRes.error(ErrorEnum.ParamError, "二维码错误！") : ApiRes.ok("验证成功！");
        } catch (LtServerException lex) {
            return ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            return ApiRes.error(ErrorEnum.ParamError, "登录失败！");
        }
    }

    /**
     * 检测二维码扫描登录的情况。
     *
     * @author yueye
     */
    @RequestMapping(value = "/checkQrCodeLogin", method = RequestMethod.POST)
    @ApiOperation(value = "检测二维码扫描登录的内容", notes = "检测生成的二维码是否已经被扫描，如果成功返回扫描用户的信息，扫描不成功返回null。", httpMethod = "POST", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "systemCode", value = "系统编号", paramType = "query", required = true, defaultValue = "thisSystem", dataType = "string")})
    public ApiRes<LoginUserMsg> checkQrCodeLogin(String systemCode) {
        if (StringUtils.isBlank(systemCode)) {
            return ApiRes.error(ErrorEnum.ParamError, "系统编号不能为空！");
        }
        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ErrorEnum.InvalidRequest, "当前系统不受单点登录系统管理!");
        }

        String ip = NetUtil.getIp(request);
        try {
            String account = LoginTokenUtil.checkQrCodeLogin(ip, systemCode);
            if (StringUtils.isBlank(account)) {
                return ApiRes.ok(null);
            }

            // 认证成功，直接返回给前台用户的信息。
            SysUser user = loginService.getUserByAccount(account);

            // 记录登录日志(异步)
            optLogService.setLogs("用户模块", "扫码登录", "扫描二维码登录", ip, user.getId(), systemCode).addOptLogsRunnable();

            LoginUserMsg userMsg = loginService.getSsoUserMsg(user, systemCode);
            return ApiRes.ok(userMsg);

        } catch (LtServerException lex) {
            return ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            return ApiRes.error(ErrorEnum.ParamError, "登录失败！");
        }
    }
}
