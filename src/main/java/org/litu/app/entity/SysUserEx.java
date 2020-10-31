package org.litu.app.entity;

/**
 * user对象的级联对象
 */
public class SysUserEx extends SysUser {

    private static final long serialVersionUID = 4235450789559208882L;

    private SysRole role;
    private SysOrganize department;

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public SysOrganize getDepartment() {
        return department;
    }

    public void setDepartment(SysOrganize department) {
        this.department = department;
    }


}
