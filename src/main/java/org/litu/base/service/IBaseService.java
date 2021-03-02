package org.litu.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.litu.core.base.BaseEntity;
import org.litu.core.exception.LtParamException;
import org.litu.core.login.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * service的基类
 *
 * @param <T>
 */
public interface IBaseService<T extends BaseEntity> extends IService<T> {

    /**
     * controller 列表方法
     *
     * @param entity  实体类
     * @param keyword 关键词
     * @param params  Map参数
     * @return controller层列表方法
     */
    List<T> list(UserInfo user, T entity, String keyword, Map<String, String> params);

    /**
     * controller 分页查询方法
     *
     * @param entity 实体类
     * @param params Map参数
     * @param page   分页信息
     * @return 分页查询
     */
    IPage<T> page(UserInfo user, T entity, String keyword, IPage<T> page, Map<String, String> params);

    /**
     * controller 获取实体详细信息
     *
     * @param id 需要获取的id
     * @return 实体详细信息
     */
    T detail(String id);

    /**
     * controller 保存
     *
     * @param entity 实体类
     * @param params Map参数
     * @return true则保存成功
     */
    boolean save(UserInfo user, T entity, Map<String, String> params);

    /**
     * controller 更新
     *
     * @param entity 实体类
     * @param params Map参数
     * @return true则更新成功
     */
    public boolean update(UserInfo user, T entity, Map<String, String> params);

    /**
     * 删除信息（物理删除）
     *
     * @param id     需要删除的
     * @param params Map参数
     * @return true为删除成功
     * @throws LtParamException       自定义参数错误
     * @throws InstantiationException 不能实例化某个对象
     * @throws IllegalAccessException 安全权限异常
     */
    boolean delete(String id, Map<String, String> params);

    /**
     * 批量物理删除信息
     *
     * @param ids    id集合，用，隔开
     * @param params Map参数
     * @return true则删除成功
     * @throws LtParamException       自定义参数错误
     * @throws InstantiationException 不能实例化某个对象
     * @throws IllegalAccessException 安全权限异常
     */
    boolean batchDelete(List<String> ids, Map<String, String> params);

    /**
     * 删除信息（逻辑删除）
     *
     * @param id     需要删除的
     * @param params Map参数
     * @return true为删除成功
     * @throws LtParamException 自定义参数错误
     */
    boolean logicalDelete(UserInfo user, String id, Map<String, String> params);

    /**
     * 逻辑恢复
     *
     * @param id
     * @param params
     * @return
     */
    boolean logicalRestore(String id, Map<String, String> params);

    /**
     * controller 是否存在
     *
     * @param id    id
     * @param value 值
     * @param field 要检查的字段
     * @return true则为存在
     */
    boolean exists(String id, String value, String field);

    /**
     * 设置单个字段的值
     *
     * @param id    数据的id
     * @param value 需要设置的值
     * @param field 字段名称
     * @return
     */
    boolean changeValue(String id, String value, String field);
}
