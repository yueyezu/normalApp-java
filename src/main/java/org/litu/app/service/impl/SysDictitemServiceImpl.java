package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.litu.app.dao.SysDictItemMapper;
import org.litu.app.entity.SysDictitem;
import org.litu.app.service.ISysDictitemService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ltgk
 * @since 2018-10-26
 */
@Service
public class SysDictitemServiceImpl extends BaseServiceImpl<SysDictItemMapper, SysDictitem> implements ISysDictitemService {

    @Override
    public void beforeList(SysDictitem entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysDictitem> query) {
        query.eq(SysDictitem::getDictId, entity.getDictId());
        query.and(i -> i.like(SysDictitem::getCode, keyword).or().like(SysDictitem::getName, keyword));
        query.orderByAsc(SysDictitem::getSortNum);
    }
}
