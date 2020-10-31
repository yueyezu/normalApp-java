package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.dao.SysSystemMapper;
import org.litu.app.entity.SysSystem;
import org.litu.app.service.ISysSystemService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统信息表 服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2019-07-24
 */
@Service
public class SysSystemServiceImpl extends BaseServiceImpl<SysSystemMapper, SysSystem> implements ISysSystemService {

    @Override
    public void beforePage(SysSystem entity, String keyword, IPage<SysSystem> page, Map<String, String> params, LambdaQueryWrapper<SysSystem> query) {
        if (StringUtils.isNotBlank(params.get("fName"))) {
            query.like(SysSystem::getfName, params.get("fName"));
        }
        if (StringUtils.isNotBlank(params.get("fType"))) {
            query.eq(SysSystem::getfType, params.get("fType"));
        }
        query.orderByDesc(SysSystem::getfCreatetime);
    }

    /**
     * 获取所有可以使用未删除的系统的列表
     *
     * @return
     */
    @Override
    public List<SysSystem> listEnabled() {
        LambdaQueryWrapper<SysSystem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysSystem::getfEnabledflag, SysContant.FLAG_TRUE);
        queryWrapper.orderByAsc(SysSystem::getfCreatetime, SysSystem::getfCode);
        return list(queryWrapper);
    }
}
