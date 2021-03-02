package org.litu.app.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.vo.LoginUserMsg;
import org.litu.base.log.IBaseLogService;
import org.litu.base.log.LtLog;
import org.litu.base.log.LtLogOperationEnum;
import org.litu.base.service.ILoginService;
import org.litu.core.base.ApiRes;
import org.litu.core.base.BaseController;
import org.litu.core.base.BaseRes;
import org.litu.core.enums.ResultEnum;
import org.litu.core.exception.LtServerException;
import org.litu.core.login.ShiroLoginUtil;
import org.litu.core.login.TokenCheck;
import org.litu.core.login.TokenUtil;
import org.litu.core.login.UserInfo;
import org.litu.util.common.CaptchaUtil;
import org.litu.util.net.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 用户登录/登出
 */
@LtLog(module = "用户登陆/登出模块")
@Controller
public class LoginController extends BaseController {

    @Autowired
    ILoginService<LoginUserMsg> loginService;
    @Autowired
    IBaseLogService optLogService;

    @Autowired
    TokenUtil tokenUtil;

    /**
     * session attr name 验证码
     */
    private final static String VCodeSessionKey = "vcode";

    /**
     * 跳转到登录页面
     *
     * @return 登陆页面
     */
    @GetMapping(value = {"", "/login"})
    @TokenCheck(check = false)
    public String login(ModelMap modelMap) {
        String token = token();

        return token == null ? "login" : "redirect:index?token=" + token;
    }

    /**
     * 获取验证码
     *
     * @throws IOException 抛出IO流异常
     * @throws Exception   抛出异常
     */
    @GetMapping("/public/vcode")
    @ResponseBody
    @TokenCheck(check = false)
    public void vcode() throws IOException {
        // 通知浏览器不要缓存
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        CaptchaUtil util = CaptchaUtil.Instance();
        // 将验证码输入到session中，用来验证
        String code = util.getString();
        ShiroLoginUtil.session(VCodeSessionKey, code);
        logger.info("生成的验证码:" + code);
        // 输出到web页面
        ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
    }

    /**
     * 用户登录
     * 当前系统，本地登陆使用接口
     *
     * @param account    账号
     * @param password   密码
     * @param verifyCode 验证码
     * @return 成功则显示登陆成功
     * @throws Exception 抛出异常
     */
    @PostMapping("/public/loginShiro")
    @ResponseBody
    @TokenCheck(check = false)
    public BaseRes loginShiro(String account, String password, String verifyCode) throws Exception {
        if (StringUtils.isAnyBlank(account, password)) {
            return BaseRes.error(ResultEnum.ParamError, "用户名或密码不能为空!");
        }
        //TODO  不需要进行验证码验证
//        String nowVCode = ShiroSessionUtil.<String>session(VCodeSessionKey);
//        if (StringUtils.isBlank(verifyCode) || !verifyCode.equalsIgnoreCase(nowVCode)) {
//            return BaseRes.error(ErrorEnum.ParamError, "验证码不正确!");
//        }
        UserInfo user = loginService.checkLoginShiro(SysContant.CURRENT_SYSTEM_CODE, account, password);
        if (user == null) {
            return BaseRes.error(ResultEnum.UserPwdError);
        }
        String ip = NetUtil.getIp(request);
        String userId = user.getId();

        String token = tokenUtil.newToken(ip, SysContant.CURRENT_SYSTEM_CODE);
        tokenUtil.setToken(token, user);

        // 登录成功后，都对验证码进行更新
//        ShiroSessionUtil.removeSession(VCodeSessionKey);
        // 登录成功后，调整用户登录状态
        loginService.ChangeLoginStatus(userId, SysContant.FLAG_TRUE);

        // 记录登录日志(异步)
        optLogService.setLogs("用户登陆/登出模块", LtLogOperationEnum.LOGIN.getOperation(), "用户登录", ip, userId).addOptLogsRunnable();
        return BaseRes.ok("登陆成功！").put("data", token);
    }

    /**
     * shiro登陆用户退出
     *
     * @return 成功则显示注销成功
     */
    @PostMapping("/public/logoutShiro")
    @ResponseBody
    @TokenCheck
    public BaseRes logoutShiro() {
        String token = token();
        UserInfo user = nowUser(token);

        boolean res = loginService.logoutShiro();
        if (res) {
            tokenUtil.delToken(token);

            loginService.ChangeLoginStatus(user.getId(), SysContant.FLAG_FALSE);

            // 更新userlogin表,记录日志(异步)
            String ip = NetUtil.getIp(request);
            optLogService.setLogs("用户模块", "退出", "退出登录", ip, user.getId()).addOptLogsRunnable();
            return BaseRes.ok("注销成功！");
        } else {
            return BaseRes.error(ResultEnum.UpdateError, "注销失败！");
        }
    }

    /*======================== 以上为登陆使用 =================================*/

    /**
     * 用户第三方登录使用， 通过用户名密码登录，并获取到单点登录的token信息。
     * <p>
     * token信息只是作为单点登录的凭证，具体登录控制需要结合sso.js文件进行。 sso.js位置在： /static/js/sso.js
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "/public/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "登录接口", notes = "用户请求，根据用户名密码获取token信息", httpMethod = "GET", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "systemCode", value = "系统编号", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "account", value = "用户账号", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "password", value = "用户密码，md5加密", paramType = "query", required = true, dataType = "string")
    })
    @ResponseBody
    @TokenCheck(check = false)
    public ApiRes<String> login(String systemCode, String account, String password) {
        if (StringUtils.isAnyBlank(account, password, systemCode)) {
            return ApiRes.error(ResultEnum.ParamError, "系统编号、用户名或密码不能为空!");
        }
        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ResultEnum.InvalidRequest, String.format("系统【%s】不受权限系统管理，请确认!", systemCode));
        }

        ApiRes result = null;
        try {
            // 获取验证登录信息以及获取用户信息
            UserInfo user = loginService.checkLogin(systemCode, account, password);
            if (user == null) {
                return ApiRes.error(ResultEnum.UserPwdError);
            }

            String ip = NetUtil.getIp(request);
            String token = tokenUtil.newToken(ip, systemCode);
            tokenUtil.setToken(token, user);

            // 记录登录日志(异步)
            optLogService.setLogs("第三方登陆", "单点登录", "用户登陆", ip, user.getId(), systemCode).addOptLogsRunnable();
            result = ApiRes.ok("登陆成功！", token);
        } catch (LtServerException se) {
            result = ApiRes.error(se.getErrorMsg(), se.getMessage());
        } catch (Exception e) {
            result = ApiRes.error(ResultEnum.UserPwdError, "用户名或密码错误!");
        }
        return result;
    }


    /**
     * 使用token信息调用该接口，获取该token对应的菜单等数据信息。
     * 如果该token失效，则直接返回失效信息
     *
     * @return
     */
    @RequestMapping(value = "/public/loginToken", method = RequestMethod.POST)
    @ApiOperation(value = "获取登陆用户，该系统的信息", notes = "使用token信息调用该接口，获取该token对应的菜单等数据信息。", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "systemCode", value = "系统编号", paramType = "query", required = true)
    })
    @TokenCheck
    public ApiRes<LoginUserMsg> loginToken(String systemCode) {
        if (StringUtils.isBlank(systemCode)) {
            return ApiRes.error(ResultEnum.ParamError, "系统编号不能为空!");
        }

        if (!loginService.checkSystemCode(systemCode)) {
            return ApiRes.error(ResultEnum.InvalidRequest, String.format("系统【%s】不受权限系统管理，请确认!", systemCode));
        }

        try {
            // 认证成功，直接返回给前台用户的信息。
            UserInfo user = nowUser();

            LoginUserMsg userMsg = loginService.getLoginMsg(user, systemCode);
            return ApiRes.ok(userMsg);
        } catch (LtServerException lex) {
            return ApiRes.error(lex.getErrorMsg(), lex.getMessage());
        } catch (Exception e) {
            return ApiRes.error(ResultEnum.ServerError, "登录失败！");
        }
    }

    /**
     * shiro登陆用户退出
     *
     * @return 成功则显示注销成功
     */
    @PostMapping("/public/logout")
    @ResponseBody
    @TokenCheck
    public BaseRes logout() {
        String token = token();
        tokenUtil.delToken(token);

        return BaseRes.ok("注销成功！");
    }
}
