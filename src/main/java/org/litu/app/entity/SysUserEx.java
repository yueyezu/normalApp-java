package org.litu.app.entity;

import lombok.Data;

/**
 * user对象的级联对象
 */
@Data
public class SysUserEx extends SysUser {

    private static final long serialVersionUID = 4235450789559208882L;

    private SysRole role;
    private SysOrganize department;
}
