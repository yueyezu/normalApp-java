package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.dao.SysLogsMapper;
import org.litu.app.entity.SysLogs;
import org.litu.app.service.ISysLogsService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统日志服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-27
 */
@Service
public class SysLogsServiceImpl extends BaseServiceImpl<SysLogsMapper, SysLogs> implements ISysLogsService {

    @Override
    public void beforePage(SysLogs sysLogs, String keyword, IPage<SysLogs> page, Map<String, String> params, LambdaQueryWrapper<SysLogs> query) {
        if (StringUtils.isNotBlank(params.get("startTime"))) {
            query.ge(SysLogs::getCreateTime, params.get("startTime"));
        }
        if (StringUtils.isNotBlank(params.get("endTime"))) {
            query.le(SysLogs::getCreateTime, params.get("endTime"));
        }

        if (sysLogs.getSystemCode() != null) {
            query.and(i -> i.like(SysLogs::getModule, keyword).or().like(SysLogs::getOptType, keyword));
            query.eq(SysLogs::getSystemCode, sysLogs.getSystemCode());
        } else {
            query.and(i -> i.like(SysLogs::getModule, keyword).or().like(SysLogs::getOptType, keyword));
        }
        query.orderByDesc(SysLogs::getCreateTime);
    }
}
