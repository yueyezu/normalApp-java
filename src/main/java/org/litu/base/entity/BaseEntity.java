package org.litu.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * entity实体的基类
 */
public abstract class BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "f_id", type = IdType.ID_WORKER_STR)
    protected String fId;

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }
}
