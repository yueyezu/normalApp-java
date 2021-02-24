package org.litu.app.controller;

import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.entity.SysUser;
import org.litu.core.base.BaseController;
import org.litu.base.service.IBaseLogService;
import org.litu.base.service.ILoginService;
import org.litu.base.util.UserUtil;
import org.litu.core.base.BaseRes;
import org.litu.core.annotation.LtLog;
import org.litu.core.annotation.LtLogOperation;
import org.litu.core.enums.ResultEnum;
import org.litu.core.enums.LtLogOperationEnum;
import org.litu.core.login.LoginTokenUtil;
import org.litu.core.login.ShiroSessionUtil;
import org.litu.util.common.CaptchaUtil;
import org.litu.util.net.NetUtil;
import org.litu.util.web.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 用户登录/登出
 */
@LtLog(module = "用户登陆/登出模块")
@Controller
public class LoginController extends BaseController {

    @Autowired
    ILoginService loginService;
    @Autowired
    IBaseLogService optLogService;

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
    public String login(ModelMap modelMap) {
        String userId = UserUtil.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            return "redirect:index";
        }

        return "login";
    }

    /**
     * 获取验证码
     *
     * @throws IOException 抛出IO流异常
     * @throws Exception   抛出异常
     */
    @GetMapping("/public/vcode")
    @ResponseBody
    public void vcode() throws IOException {
        // 通知浏览器不要缓存
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        CaptchaUtil util = CaptchaUtil.Instance();
        // 将验证码输入到session中，用来验证
        String code = util.getString();
        ShiroSessionUtil.session(VCodeSessionKey, code);
        logger.info("生成的验证码:" + code);
        // 输出到web页面
        ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
    }

    /**
     * 用户登录
     *
     * @param account    账号
     * @param password   密码
     * @param verifyCode 验证码
     * @return 成功则显示登陆成功
     * @throws Exception 抛出异常
     */
    @LtLogOperation(operation = LtLogOperationEnum.LOGIN)
    @PostMapping("/public/login")
    @ResponseBody
    public BaseRes login(String account, String password, String verifyCode) throws Exception {
        if (StringUtils.isAnyBlank(account, password)) {
            return BaseRes.error(ResultEnum.ParamError, "用户名或密码不能为空!");
        }
        //TODO  不需要进行验证码验证
//        String nowVCode = ShiroSessionUtil.<String>session(VCodeSessionKey);
//        if (StringUtils.isBlank(verifyCode) || !verifyCode.equalsIgnoreCase(nowVCode)) {
//            return BaseRes.error(ErrorEnum.ParamError, "验证码不正确!");
//        }
        SysUser user = loginService.checkLoginShiro(SysContant.CURRENT_SYSTEM_CODE, account, password);
        if (user == null) {
            return BaseRes.error(ResultEnum.UserPwdError);
        }
        String ip = NetUtil.getIp(request);

        // 登录在cookie的顶级加入登录的token信息。其他系统登录使用。
        String userId = user.getId();
        String loginToken = LoginTokenUtil.getSSOToken(userId, account, ip);
        CookieUtil.addCookie(response, SysContant.SSO_COOKIE_KEY, loginToken, 240, "/");

        // 将userId放入session
        ShiroSessionUtil.session(SysContant.SESSION_CURRENT_USERID, userId);
        ShiroSessionUtil.session("nickName", user.getRealName());

        // 登录成功后，都对验证码进行更新
        ShiroSessionUtil.removeSession(VCodeSessionKey);
        // 登录成功后，调整用户登录状态
        loginService.ChangeLoginStatus(userId, SysContant.FLAG_TRUE);

        // 记录登录日志(异步)
        optLogService.setLogs("用户模块", "登录", "用户登录", ip, userId).addOptLogsRunnable();
        return BaseRes.ok("登录成功！");
    }

    /**
     * 用户退出
     *
     * @return 成功则显示注销成功
     */
    @LtLogOperation(operation = LtLogOperationEnum.LOGOUT)
    @PostMapping("/logout")
    @ResponseBody
    public BaseRes logout() {
        String userId = UserUtil.getUserId();
        String ip = NetUtil.getIp(request);

        boolean res = loginService.logoutShiro();
        if (res) {
            loginService.ChangeLoginStatus(userId, SysContant.FLAG_FALSE);

            // 更新userlogin表,记录日志(异步)
            optLogService.setLogs("用户模块", "退出", "退出登录", ip, userId).addOptLogsRunnable();
            return BaseRes.ok("注销成功！");
        } else {
            return BaseRes.error(ResultEnum.UpdateError, "注销失败！");
        }
    }
}
