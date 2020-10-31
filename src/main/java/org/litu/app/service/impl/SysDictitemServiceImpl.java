package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.litu.app.dao.SysDictitemMapper;
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
public class SysDictitemServiceImpl extends BaseServiceImpl<SysDictitemMapper, SysDictitem> implements ISysDictitemService {

    @Override
    public void beforeList(SysDictitem entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysDictitem> query) {
        query.eq(SysDictitem::getfDictid, entity.getfDictid());
        query.and(i -> i.like(SysDictitem::getfCode, keyword).or().like(SysDictitem::getfName, keyword));
        query.orderByAsc(SysDictitem::getfSortnum);
    }
}
