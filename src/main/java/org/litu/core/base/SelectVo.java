package org.litu.core.base;

import java.util.HashMap;
import java.util.List;

/**
 * 下拉选择框
 */
public class SelectVo<T> extends HashMap<String, Object> {

    public SelectVo(String value, String label) {
        this.put("value", value);
        this.put("label", label);
    }

    public void setDisabled(Boolean disabled) {
        this.put("disabled", disabled);
    }

    public void setChildren(List<SelectVo> children) {
        this.put("children", children);
    }
}
