package org.litu.core.enums;

public enum LtLogOperationEnum {
    NONE("未知"),
    ADD("添加"), UPDATE("修改"),
    DELETE("删除"), LOGICDELETE("逻辑删除"), BATCHDELETE("批量删除"), LOGICRESTORE("逻辑恢复"),
    ENABLE("启用"), DISABLE("禁用"),
    LOGIN("登录"), LOGOUT("退出"),
    UPLOAD("上传"), DOWNLOAD("下载"),
    IMPORT("导入"), EXPORT("导出"),
    CLEAR("清空"), MODIFYPWD("修改密码"),
    STATUSSWITCH("状态切换"),
    SAVEBATCH("批量保存");

    private final String operation;

    private LtLogOperationEnum(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
