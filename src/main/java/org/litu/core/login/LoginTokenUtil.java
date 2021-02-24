package org.litu.core.login;

import org.apache.commons.lang3.StringUtils;
import org.litu.core.enums.ResultEnum;
import org.litu.core.exception.LtServerException;
import org.litu.util.Util;
import org.litu.util.barcode.QRCodeUtil;
import org.litu.util.security.DESUtil;
import org.litu.util.security.MD5Util;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方授权token生成的规则方法。
 *
 * @author yueye
 */
public class LoginTokenUtil {

    /*---------------- 用户登录用密码密钥的生成操作 -----------------*/

    //TODO 想办法转移到配置文件
    public static String defaultPwd = "111111";

    /**
     * 获取用户密码加密的密钥
     *
     * @return
     */
    public static String GetSecretkey() {
        return MD5Util.MD5Encode_16(Util.CreateNo());
    }

    /**
     * 获取用户的数据库存储的密码,默认密码
     *
     * @return
     */
    public static String GetDbPassword(String secretKey) throws Exception {
        String password = MD5Util.MD5Encode_32(defaultPwd);
        String dbPassword = MD5Util.MD5Encode_32(DESUtil.encrypt(password.toLowerCase(), secretKey.toLowerCase()).toLowerCase());
        return dbPassword;
    }

    /// <summary>
    /// 获取用户的数据库存储的密码,密文
    /// 这里如果用户的密码不录入，则认为是默认密码
    /// </summary>
    /// <param name="secretKey">加密的密钥</param>
    /// <param name="password">密码明文,如果插入的为null，则使用默认密码</param>
    /// <returns>加密后的密码密文</returns>
    public static String GetDbPassword(String secretKey, String password) throws Exception {
        String dbPassword = MD5Util.MD5Encode_32(DESUtil.encrypt(password.toLowerCase(), secretKey.toLowerCase()).toLowerCase());
        return dbPassword;
    }

    public static Boolean isDefaultPwd(String password) {
        String defPassword = MD5Util.MD5Encode_32(defaultPwd);

        return defPassword.equals(password);
    }


    /*----------------- 第三方授权登录的token生成、 操作  -------------------*/

    // TODO 第三方的token，后续将该方法调整的token复杂度高些。
    public static String getToken(String clientType, String clientMac, String clientmCode) {
        String guid = Util.GuId32();
        return guid;
    }

    public static String getRefrashToken(String clientType, String clientMac, String clientmCode) {
        String guid = Util.GuId32();
        return guid;
    }

    /**
     * 校验token是否一致
     *
     * @param token    token
     * @param nowToken 现在的token
     * @return 若为true则token一致
     */
    public static boolean checkToken(String token, String nowToken) {
        return token.equals(nowToken);
    }

    /*------------ 单点登录的token生成的操作  ---------------*/

    /**
     * 单点登录缓存的信息
     * <p>
     * TODO 这里后续可以考虑缓存或者数据库形式，因为数据需要短期的缓存，需要超时后自动清除的。 目前先使用定时任务，对该变量内容进行清理。
     * <p>
     * key为生成的logintoken信息 value为用户登录的缓存信息，可以后续做登录控制使用。
     */
    private static Map<String, SsoTempModel> ssoTemps = new HashMap<String, SsoTempModel>();

    /**
     * 获取token内容,并将token保存到缓存中
     *
     * @param account 说明
     * @param userId  用户id
     * @param ip      ip地址
     * @return 新增的token
     */
    public static String getSSOToken(String userId, String account, String ip) {
        String tokenStr = account + Util.GuId32();
        String loginToken = Base64Utils.encodeToString(tokenStr.getBytes());
        SsoTempModel ssoModel = new SsoTempModel();
        ssoModel.setAccount(account);
        ssoModel.setUserId(userId);
        ssoModel.setIp(ip);
        ssoModel.setAddTime(new Date());
        ssoModel.setToken(loginToken);

        synchronized (ssoTemps) {
            ssoTemps.put(loginToken, ssoModel);
        }

        return loginToken;
    }

    /**
     * 解析token内容。并验证登录情况，如果登录是允许的，则直接返回用户的帐号，如果登录不允许，则直接抛出异常
     *
     * @param loginToken 需要解析的token内容
     * @return 用户的账号
     */
    public static String checkSSOToken(String loginToken, String ip) {
        SsoTempModel ssoModel = null;
        synchronized (ssoTemps) {
            if (!ssoTemps.containsKey(loginToken)) {
                throw new LtServerException("单点登录信息错误，请重新登录！", ResultEnum.ParamError);
            }
            ssoModel = ssoTemps.get(loginToken);
            // 校验IP是否一致，防止被模拟请求
            if (!ip.equals(ssoModel.getIp())) {
                throw new LtServerException("单点登录信息错误，请重新登录！", ResultEnum.ParamError);
            }
            // TODO 时间这部分还需要再处理下。拿到配置中去
            Date nowTime = new Date();
            long logincha = nowTime.getTime() - ssoModel.getAddTime().getTime();
            if (logincha >= 30 * 60 * 1000) { // 登录信息超过30分钟，则需要重新登录
                // 超时后，将对应的token在缓存中清除掉。
                ssoTemps.remove(loginToken);
                throw new LtServerException("登录时间超时，请重新登录！", ResultEnum.ParamError);
            }
        }

        return ssoModel.getAccount();
    }

    /**
     * 移除单点登录的记录信息。即注销退出。
     *
     * @param loginToken 传入登陆token
     * @return 返回当前登陆信息的用户id
     */
    public static String removeSSOToken(String loginToken) {
        SsoTempModel ssoModel = null;
        synchronized (ssoTemps) {
            if (!ssoTemps.containsKey(loginToken)) {
                throw new LtServerException("单点登录信息错误，请重新登录！", ResultEnum.ParamError);
            }
            ssoModel = ssoTemps.remove(loginToken);
        }
        return ssoModel.getUserId();
    }

    /**
     * 清理单点登录记录的冗余信息。
     * <p>
     * 即在缓存中存在超过24小时的登录信息。
     *
     * @return true为清除相对token
     */
    public static boolean cleanSSOTemps() {
        SsoTempModel ssoModel = null;
        Date nowTime = new Date();
        synchronized (ssoTemps) {
            for (String loginToken : ssoTemps.keySet()) {
                ssoModel = ssoTemps.get(loginToken);
                // TODO 这里的时间后续需要处理下
                long logincha = nowTime.getTime() - ssoModel.getAddTime().getTime();
                if (logincha >= 24 * 60 * 60 * 1000) { // 登录信息超过一天，则需要重新登录
                    // 超时后，将对应的token在缓存中清除掉。
                    ssoTemps.remove(loginToken);
                }
            }
        }
        return true;
    }

    /*-------------二维码登录使用标识存储--------------*/
    /**
     * 二维码登录信息缓冲的内部变量, key为ip, value为二维码相关的信息。
     * <p>
     * TODO 这里后续可以考虑本地数据库redis的使用。
     */
    private static Map<String, QrCodeTempModel> qrCodeTemps = new HashMap<String, QrCodeTempModel>();

    /**
     * 获取二维码，如果已经存在了，则直接刷新，如果存在时间过长了，则直接刷新为新的信息。
     *
     * @param ip ip地址
     * @return 输出流的信息
     * @throws Exception 抛出异常
     * @author yueye
     */
    public static ByteArrayOutputStream getQrCode(String ip, String systemCode) throws Exception {
        String qrStr = "";
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        QrCodeTempModel tempModel = null;
        synchronized (qrCodeTemps) {
            if (qrCodeTemps.containsKey(ip)) {
                tempModel = qrCodeTemps.get(ip);
                qrStr = tempModel.refresh();
            } else {
                tempModel = new QrCodeTempModel(ip, systemCode);
                qrCodeTemps.put(ip, tempModel);
                qrStr = tempModel.getQrStr();
            }
        }

        QRCodeUtil.encode(qrStr, output);

        return output;
    }

    /**
     * 根据扫码获取二维码，判断是否二维码有效
     *
     * @param code    号码
     * @param account 用户账号
     * @return true则说明二维码有效
     * @author yueye
     */
    public static boolean checkQrCode(String code, String account) {
        boolean checkResult = false;
        synchronized (qrCodeTemps) {
            for (String ip : qrCodeTemps.keySet()) {
                QrCodeTempModel tempModel = qrCodeTemps.get(ip);
                if (code.equals(tempModel.getQrStr())) {
                    Date refreshTime = tempModel.getRefreshDate();
                    Date nowTime = new Date();

                    long cha = nowTime.getTime() - refreshTime.getTime();
                    if (cha >= 120 * 1000) { // 二维码超过2分钟,则认为失效
                        throw new LtServerException("二维码失效，请重新获取！", ResultEnum.ParamError);
                    }

                    if (StringUtils.isNotBlank(tempModel.getLoginAccount())) {
                        throw new LtServerException("二维码已经扫描登录，请等待！", ResultEnum.ParamError);
                    }

                    tempModel.setLoginAccount(account);
                    checkResult = true;
                    break;
                }
            }
        }

        return checkResult;
    }

    /**
     * 获取二维码方检测，二维码是否被扫描登录
     *
     * @param ip 传入ip，通过ip检测二维码是否被扫描登陆
     * @return 用户账号
     * @author yueye
     */
    public static String checkQrCodeLogin(String ip, String systemCode) {
        String account = null;
        synchronized (qrCodeTemps) {
            if (qrCodeTemps.containsKey(ip)) {
                QrCodeTempModel tempModel = qrCodeTemps.get(ip);
                if (systemCode.equals(tempModel.getsystemCode())) {
                    account = tempModel.getLoginAccount();

                    qrCodeTemps.remove(ip);
                }
            } else {
                throw new LtServerException("二维码错误，请重新获取", ResultEnum.ParamError);
            }
        }

        return account;
    }


    /**
     * 清理二维码登录记录的冗余信息。
     * <p>
     * 即在缓存中存在超过24小时的登录信息。
     *
     * @return true则说明登陆token被清除
     */
    public static boolean cleanQrCodeTemps() {
        QrCodeTempModel tempModel = null;
        Date nowTime = new Date();
        synchronized (qrCodeTemps) {
            for (String ip : qrCodeTemps.keySet()) {
                tempModel = qrCodeTemps.get(ip);
                // TODO 这里的时间后续需要处理下
                long logincha = nowTime.getTime() - tempModel.getRefreshDate().getTime();
                if (logincha >= 24 * 60 * 60 * 1000) { // 登录信息超过一天，则需要重新登录
                    // 超时后，将对应的token在缓存中清除掉。
                    ssoTemps.remove(ip);
                }
            }
        }
        return true;
    }
}

/**
 * 单点登录信息缓存的信息模版。
 *
 * @author yueye
 */
class SsoTempModel {
    private String ip; // 验证用条件
    private Date addTime; // 验证用条件
    private String token; // 登录loginToken

    private String account; // 登录最后反馈的结果
    private String userId; // 用户ID

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

/**
 * 二维码登录信息缓存的类
 *
 * @author yueye
 */
class QrCodeTempModel {
    private String ip; // 获取二维码的识别标识。
    private String systemCode; // 当前识别二维码对应的系统编号
    private String qrStr;
    private Date addTime;
    private Date refreshTime; // 控制刷新多次之后，智能隔一段时间再进行刷新。
    private int createNum = 0; // 一分钟内刷新超过5次之后，需要隔一段时间再进行刷新。
    private String loginAccount; // 这里用来存储当前扫描该二维码登录的账户信息，如果为空表示还未登陆，如果不为空，则表示已经登陆。

    public QrCodeTempModel(String ip, String systemCode) {
        String qrStr = getNewQrCode();

        this.ip = ip;
        this.systemCode = systemCode;
        this.addTime = new Date();
        this.refreshTime = this.addTime;
        this.qrStr = qrStr;
        this.loginAccount = "";
        this.createNum = 1;
    }

    /**
     * 刷新二维码，同时二维码刷新情况进行校验。
     *
     * @return 新二维码链接地址
     * @author yueye
     */
    public String refresh() {
        Date nowDate = new Date();

        long cha = nowDate.getTime() - addTime.getTime();
        if (cha < 1000 * 60) { // TODO 这里时间可以处理拿到配置中。
            if (this.createNum >= 5) {
                throw new LtServerException("请求过于频繁，请1分钟后重试！", ResultEnum.InvalidRequest);
            }
            this.addTime = nowDate;
            this.loginAccount = "";
            this.createNum = 0;
        }

        this.createNum += 1;
        this.refreshTime = nowDate;
        this.qrStr = getNewQrCode();
        this.loginAccount = "";

        return qrStr;
    }

    private static String getNewQrCode() {
        String qrStr = Util.GuId32();
        qrStr = qrStr.replace("-", "@");
        return qrStr;
    }

    public String getQrStr() {
        return qrStr;
    }

    public String getIp() {
        return ip;
    }

    public String getsystemCode() {
        return systemCode;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public Date getRefreshDate() {
        return refreshTime;
    }
}
