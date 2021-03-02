package org.litu.app.service.baseimpl;

import org.litu.app.constant.SysContant;
import org.litu.app.entity.SysLogs;
import org.litu.app.service.ISysLogsService;
import org.litu.base.log.IBaseLogService;
import org.litu.base.log.LtLogsVo;
import org.litu.base.util.ThreadUtil;
import org.litu.core.exception.LtParamException;
import org.litu.core.login.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 线程操作，记录系统日志。
 *
 * @author yueye
 */
@Service
public class BaseLogService implements IBaseLogService {
    @Autowired
    private ISysLogsService sysLogService;

    private LtLogsVo logsVo = null;

    @Override
    public BaseLogService setLogs(UserInfo user, String module, String optType, String optContent, String ip) {
        LtLogsVo tempLogsVo = new LtLogsVo();
        tempLogsVo.setSystemCode(SysContant.CURRENT_SYSTEM_CODE);
        tempLogsVo.setModule(module);
        tempLogsVo.setOptcontent(optContent);
        tempLogsVo.setOpttype(optType);
        tempLogsVo.setIp(ip);
        tempLogsVo.setUserId(user.getId());

        this.logsVo = tempLogsVo;
        return this;
    }

    @Override
    public BaseLogService setLogs(String module, String optType, String optContent, String ip, String userId) {
        LtLogsVo tempLogsVo = new LtLogsVo();

        tempLogsVo.setSystemCode(SysContant.CURRENT_SYSTEM_CODE);
        tempLogsVo.setModule(module);
        tempLogsVo.setOptcontent(optContent);
        tempLogsVo.setOpttype(optType);
        tempLogsVo.setIp(ip);
        tempLogsVo.setUserId(userId);

        this.logsVo = tempLogsVo;

        return this;
    }

    @Override
    public BaseLogService setLogs(String module, String optType, String optContent, String ip, String userId, String systemCode) {
        LtLogsVo tempLogsVo = new LtLogsVo();

        tempLogsVo.setSystemCode(systemCode);
        tempLogsVo.setModule(module);
        tempLogsVo.setOptcontent(optContent);
        tempLogsVo.setOpttype(optType);
        tempLogsVo.setIp(ip);
        tempLogsVo.setUserId(userId);

        this.logsVo = tempLogsVo;

        return this;
    }

    @Override
    public BaseLogService setLogs(LtLogsVo tempLogsVo) {
        this.logsVo = tempLogsVo;

        return this;
    }

    /**
     * 直接对日志进行记录
     */
    @Override
    public boolean addOptLogs() {
        if (this.logsVo == null) {
            throw new LtParamException("需要记录的日志信息未设置");
        }

        SysLogs log = new SysLogs();
        log.setSystemCode(this.logsVo.getsystemCode());
        log.setModule(this.logsVo.getModule());
        log.setOptType(this.logsVo.getOpttype());
        log.setOptContent(this.logsVo.getOptcontent());
        log.setIpAddr(this.logsVo.getIp());
        log.setCreateBy(this.logsVo.getUserId());
        log.setCreateTime(new Date());

        return sysLogService.save(log);
    }

    /**
     * 直接对日志进行记录
     */
    @Override
    public boolean addOptLogs(LtLogsVo optLogs) {
        SysLogs log = new SysLogs();
        log.setSystemCode(optLogs.getsystemCode());
        log.setModule(optLogs.getModule());
        log.setOptType(optLogs.getOpttype());
        log.setOptContent(optLogs.getOptcontent());
        log.setIpAddr(optLogs.getIp());
        log.setCreateBy(optLogs.getUserId());
        log.setCreateTime(new Date());

        return sysLogService.save(log);
    }

    /**
     * 启动线程进行日志信息记录
     */
    @Override
    public void addOptLogsRunnable() {
        if (this.logsVo == null) {
            throw new LtParamException("需要记录的日志信息未设置！");
        }

        ThreadUtil.fixedThreadPool.execute(new AddLogsRunnable(logsVo));
    }

    private class AddLogsRunnable implements Runnable {
        LtLogsVo logsVo = new LtLogsVo();

        public AddLogsRunnable(LtLogsVo logsVo) {
            this.logsVo = logsVo;
        }

        @Override
        public void run() {
            SysLogs log = new SysLogs();
            log.setSystemCode(logsVo.getsystemCode());
            log.setModule(logsVo.getModule());
            log.setOptType(logsVo.getOpttype());
            log.setOptContent(logsVo.getOptcontent());
            log.setIpAddr(logsVo.getIp());
            log.setCreateBy(logsVo.getUserId());
            log.setCreateTime(new Date());

            sysLogService.save(log);
        }
    }
}
