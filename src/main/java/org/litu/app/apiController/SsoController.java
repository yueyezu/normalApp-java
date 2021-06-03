package org.litu.app.apiController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.entity.vo.LoginUserMsg;
import org.litu.base.log.IBaseLogService;
import org.litu.base.service.ILoginService;
import org.litu.core.base.ApiRes;
import org.litu.core.base.BaseController;
import org.litu.core.enums.ResultEnum;
import org.litu.core.exception.LtServerException;
import org.litu.core.login.QRCodeLoginUtil;
import org.litu.core.login.UserInfo;
import org.litu.util.net.NetUtil;
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
public class SsoController extends BaseController {

    @Autowired
    private ILoginService<LoginUserMsg> loginService;
    @Autowired
    private IBaseLogService optLogService;

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
            return ApiRes.error(ResultEnum.ParamError, "系统编号参数不能为空！");
        }
        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ResultEnum.InvalidRequest, "当前系统不受单点登录系统管理!");
        }

        try {
            String ip = NetUtil.getIp(request);
            ByteArrayOutputStream qrCodeOut = QRCodeLoginUtil.getQrCode(ip, systemCode);

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
            return ApiRes.error(ResultEnum.ServerError, "二维码生成失败！");
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
            return ApiRes.error(ResultEnum.ParamError, "二维码和用户账号信息不能为空！");
        }

        try {
            // 认证成功，直接返回给前台用户的信息。
            UserInfo user = loginService.getUserByAccount(account);
            if (user == null) {
                ApiRes.error(ResultEnum.UserPwdError, "授权服务器，未找到当前用户信息。");
            }

            boolean result = QRCodeLoginUtil.checkQrCode(qrCode, account);

            return !result ? ApiRes.error(ResultEnum.ParamError, "二维码错误！") : ApiRes.ok("验证成功！");
        } catch (LtServerException lex) {
            return ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            return ApiRes.error(ResultEnum.ParamError, "登录失败！");
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
            return ApiRes.error(ResultEnum.ParamError, "系统编号不能为空！");
        }
        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ResultEnum.InvalidRequest, "当前系统不受单点登录系统管理!");
        }

        String ip = NetUtil.getIp(request);
        try {
            String account = QRCodeLoginUtil.checkQrCodeLogin(ip, systemCode);
            if (StringUtils.isBlank(account)) {
                return ApiRes.ok(null);
            }

            // 认证成功，直接返回给前台用户的信息。
            UserInfo user = loginService.getUserByAccount(account);

            // 记录登录日志(异步)
            optLogService.setLogs("用户模块", "扫码登录", "扫描二维码登录", ip, user.getId(), systemCode).addOptLogsRunnable();

            LoginUserMsg userMsg = loginService.getLoginMsg(user, systemCode);
            return ApiRes.ok(userMsg);

        } catch (LtServerException lex) {
            return ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            return ApiRes.error(ResultEnum.ParamError, "登录失败！");
        }
    }
}
