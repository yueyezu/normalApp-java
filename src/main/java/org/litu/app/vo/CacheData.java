package org.litu.app.vo;

import lombok.Data;
import org.litu.core.base.SelectVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 前台缓存按钮和字典
 */
@Data
public class CacheData implements Serializable {
    // 用户ID
    private String userId;
    // 字典信息列表——下拉用
    private Map<String, Map<String, String>> dictItems;
    // 字典信息列表——展示用
    private Map<String, List<SelectVo>> dictItemArr;
}
