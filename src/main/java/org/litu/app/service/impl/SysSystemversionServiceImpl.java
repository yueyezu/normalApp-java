package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.dao.SysSystemversionMapper;
import org.litu.app.entity.system.SysSystemversion;
import org.litu.app.service.ISysSystemversionService;
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
public class SysSystemversionServiceImpl extends BaseServiceImpl<SysSystemversionMapper, SysSystemversion> implements ISysSystemversionService {

    @Override
    public void beforePage(UserInfo user, SysSystemversion entity, String keyword, IPage<SysSystemversion> page, Map<String, String> params, LambdaQueryWrapper<SysSystemversion> query) {
        query.eq(SysSystemversion::getSystemCode, params.get("versionCode"));//查询相应系统的不同版本数据
        if (StringUtils.isNotBlank(params.get("version"))) {
            query.like(SysSystemversion::getVersion, params.get("version"));
        }
        query.orderByDesc(SysSystemversion::getCreateTime);
    }
}
