package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.constant.SysContant;
import org.litu.app.dao.SysOrganizeMapper;
import org.litu.app.entity.SysOrganize;
import org.litu.app.service.ISysOrganizeService;
import org.litu.base.service.impl.BaseTreeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 部门服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-25
 */
@Service
public class SysOrganizeServiceImpl extends BaseTreeServiceImpl<SysOrganizeMapper, SysOrganize> implements ISysOrganizeService {

    @Override
    public void beforeTree(SysOrganize sysOrganize, String keyword, Map<String, String> params, LambdaQueryWrapper<SysOrganize> query) {
        if (!StringUtils.isBlank(keyword)) {
            query.and(i -> i.like(SysOrganize::getfCode, keyword).or().like(SysOrganize::getfName, keyword));
        }
        if (!StringUtils.isBlank(sysOrganize.getfParentid())) {
            // 如果根据父类来进行查询的话，需要使用layer来进行比较
            query.like(SysOrganize::getfLayers, sysOrganize.getfParentid());
        }
    }
}
