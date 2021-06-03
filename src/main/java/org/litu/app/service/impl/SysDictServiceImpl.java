package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.litu.app.dao.SysDictMapper;
import org.litu.app.dao.SysDictItemMapper;
import org.litu.app.entity.system.SysDict;
import org.litu.app.entity.system.SysDictitem;
import org.litu.app.service.ISysDictService;
import org.litu.base.service.impl.BaseTreeServiceImpl;
import org.litu.core.exception.LtParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-24
 */
@Service
public class SysDictServiceImpl extends BaseTreeServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictItemMapper dictItemMapper;

    @Override
    public void beforeTree(SysDict entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysDict> query) {
        if (!StringUtils.isBlank(keyword)) {
            query.and(i -> i.like(SysDict::getCode, keyword).or().like(SysDict::getName, keyword));
        }
        if (!StringUtils.isBlank(entity.getParentId())) {
            // 如果根据父类来进行查询的话，需要使用layer来进行比较
            query.like(SysDict::getLayers, entity.getParentId());
        }
    }

    @Override
    public boolean beforeDelete(String id, Map<String, String> params, SysDict entity) {
        // 判断当前字段是否存在下级字段，如果存在，则不允许删除
        LambdaQueryWrapper<SysDictitem> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysDictitem::getDictId, id);
        List<SysDictitem> dictItems = dictItemMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(dictItems)) {
            throw new LtParamException("当前字典非空!");
        }

        return super.beforeDelete(id, params, entity);
    }
}
