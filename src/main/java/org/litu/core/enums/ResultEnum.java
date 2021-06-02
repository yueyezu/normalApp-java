package org.litu.core.enums;

public enum ResultEnum {
    Success("200", "200", "操作成功！", "Success"),
    ServerError("500", "-32603", "服务器内部错误！", "Internal error"),
    ParseError("405", "-32700", "协议解析错误!", "Parse error Invalid,Method not allowed"),
    Unauthorized("401", "-32600", "用户未提供身份验证凭据！", "Unauthorized"),
    InvalidRequest("403", "-32600", "没有权限，请联系管理员授权！", "Invalid Request"),
    NotFound("404", "-32601", "所请求的资源不存在!", "Method not found"),
    ParamError("400", "-32602", "请求参数错误！", "Invalid params"),

    //-- -000-099 为预留的业务相关的错误提示内容
    //第三方授权相关错误提示
    ClientError("010", "-32010", "客户端信息错误！", "client id or secret has error"),
    GetTokenError("011", "-32011", "获取授权码错误！", "get token error"),
    GetRefreshTokenError("012", "-32012", "获取刷新码错误！", "get refresh token error"),
    TokenError("013", "-32013", "登陆信息错误！", "token error"),
    TokenTimeout("014", "-32014", "登陆信息已经超期！", "token has out of time"),

    //登录相关的错误提示
    UserPwdError("016", "-32016", "用户名或密码错误", "account or password error"),
    UserNotEnable("017", "-32017", "用户被禁用或者没有登录权限", "user can`t login or no login auth"),
    UserHasNoAuth("018", "-32018", "用户当前用户没有权限", "user has no auth"),

    //业务操作相关的错误提醒
    // 常规业务操作
    SaveError("021", "-32021", "保存失败！", "Save error"),
    UpdateError("022", "-32022", "更新失败！", "Update error"),
    DeleteError("023", "-32023", "删除失败！", "Delete error"),
    SearchError("024", "-32024", "查询失败！", "Search error"),
    ImportError("025", "-32025", "导入失败！", "Import error"),
    ExportError("026", "-32026", "导出失败！", "Export error"),
    // 其他提示
    DuplicateError("027", "-32027", "已存在重复数据！", "Export error");


    private final String code;
    private final String rcpCode;
    private final String text;
    private final String enText;

    private ResultEnum(String code, String rcpCode, String text, String enText) {
        this.code = code;
        this.rcpCode = rcpCode;
        this.text = text;
        this.enText = enText;
    }

    public String getCode() {
        return code;
    }

    public String getRpcCode() {
        return rcpCode;
    }

    public String getText() {
        return text;
    }

    public String getEnText() {
        return enText;
    }

    public String toString() {
        return code;
    }
}
