package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.dao.SysSystemVersionMapper;
import org.litu.app.entity.system.SysSystemVersion;
import org.litu.app.service.ISysSystemVersionService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.litu.core.login.UserInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统版本管理表 服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2019-07-24
 */
@Service
public class SysSystemVersionServiceImpl extends BaseServiceImpl<SysSystemVersionMapper, SysSystemVersion> implements ISysSystemVersionService {

    @Override
    public void beforePage(UserInfo user, SysSystemVersion entity, String keyword, IPage<SysSystemVersion> page, Map<String, String> params, LambdaQueryWrapper<SysSystemVersion> query) {
        query.eq(SysSystemVersion::getSystemCode, params.get("versionCode"));//查询相应系统的不同版本数据
        if (StringUtils.isNotBlank(params.get("version"))) {
            query.like(SysSystemVersion::getVersion, params.get("version"));
        }
        query.orderByDesc(SysSystemVersion::getCreateTime);
    }
}
