package org.litu.core.login;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.litu.core.enums.ResultEnum;
import org.litu.core.exception.LtServerException;
import org.litu.util.Util;
import org.litu.util.barcode.QRCodeUtil;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方授权token生成的规则方法。
 *
 * @author yueye
 */
public class QRCodeLoginUtil {

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
                    Date refreshTime = tempModel.getRefreshTime();
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
                if (systemCode.equals(tempModel.getSystemCode())) {
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
                long logincha = nowTime.getTime() - tempModel.getRefreshTime().getTime();
                if (logincha >= 24 * 60 * 60 * 1000) { // 登录信息超过一天，则需要重新登录
                    // 超时后，将对应的token在缓存中清除掉。
                    qrCodeTemps.remove(ip);
                }
            }
        }
        return true;
    }
}

/**
 * 二维码登录信息缓存的类
 *
 * @author yueye
 */
@Data
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
}
