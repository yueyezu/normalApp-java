package org.litu.app.service;

import org.litu.app.entity.system.SysSystem;
import org.litu.base.service.IBaseService;

import java.util.List;

/**
 * <p>
 * 系统信息表 服务类
 * </p>
 *
 * @author ltgk
 * @since 2019-07-24
 */
public interface ISysSystemService extends IBaseService<SysSystem> {

    /**
     * 获取所有可以使用未删除的系统的列表
     *
     * @return
     */
    List<SysSystem> listEnabled();
}
