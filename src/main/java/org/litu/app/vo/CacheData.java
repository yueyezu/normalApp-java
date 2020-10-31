package org.litu.app.vo;

import org.litu.base.vo.SelectVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 前台缓存按钮和字典
 */
public class CacheData implements Serializable {
    private static final long serialVersionUID = 3757132400237502192L;

    // 用户ID
    private String userId;
    // 是否是默认密码
    private String isDefaultPwd;
    // 字典信息列表——下拉用
    private Map<String, Map<String, String>> dictItems;
    // 字典信息列表——展示用
    private Map<String, List<SelectVo>> dictItemArr;

    /**
     * 获取用户ID
     *
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取是否是默认密码
     *
     * @return
     */
    public String getIsDefaultPwd() {
        return isDefaultPwd;
    }

    /**
     * 设置是否是默认密码
     *
     * @param isDefaultPwd
     */
    public void setIsDefaultPwd(String isDefaultPwd) {
        this.isDefaultPwd = isDefaultPwd;
    }

    /**
     * 获取字典信息列表
     *
     * @return 字典信息列表
     */
    public Map<String, Map<String, String>> getDictItems() {
        return dictItems;
    }

    /**
     * 设置字典信息列表
     *
     * @param dictItems 需要设置的字典信息列表
     */
    public void setDictItems(Map<String, Map<String, String>> dictItems) {
        this.dictItems = dictItems;
    }

    /**
     * 获取字典信息列表
     *
     * @return 字典信息列表
     */
    public Map<String, List<SelectVo>> getDictItemArr() {
        return dictItemArr;
    }

    /**
     * 设置字典信息列表
     *
     * @param dictItemArr 需要设置的字典信息列表
     */
    public void setDictItemArr(Map<String, List<SelectVo>> dictItemArr) {
        this.dictItemArr = dictItemArr;
    }

}
