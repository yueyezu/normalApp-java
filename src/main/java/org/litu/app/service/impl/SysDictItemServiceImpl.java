package org.litu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.litu.app.dao.SysDictItemMapper;
import org.litu.app.entity.system.SysDictItem;
import org.litu.app.service.ISysDictItemService;
import org.litu.base.service.impl.BaseServiceImpl;
import org.litu.core.login.UserInfo;
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
public class SysDictItemServiceImpl extends BaseServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

    @Override
    public void beforeList(UserInfo user, SysDictItem entity, String keyword, Map<String, String> params, LambdaQueryWrapper<SysDictItem> query) {
        query.eq(SysDictItem::getDictId, entity.getDictId());
        query.and(i -> i.like(SysDictItem::getCode, keyword).or().like(SysDictItem::getName, keyword));
        query.orderByAsc(SysDictItem::getSortNum);
    }
}
