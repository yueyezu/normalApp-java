package org.litu.core.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.litu.core.enums.ResultEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 对外开放的接口返回实体类。主要是该类可以通过swagger直接转化。
 * ApiRes的规范性要求更高。
 * BaseRes的灵活性更高。
 *
 * @param <T>
 */
@ApiModel(value = "接口交互实体", description = "对于Api接口之间交互进行协议的定义！")
public class ApiRes<T> implements Serializable {

    private static final long serialVersionUID = 453531L;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码,200表示成功,500服务器错误，602参数错误，600授权错误，601未找到方法", required = true)
    protected ResultEnum res;
    /**
     * 说明信息
     */
    @ApiModelProperty(value = "说明信息,错误的描述信息", required = true)
    private String msg;
    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回的数据信息,具体格式根据不同接口确定。")
    private T data;

    public ResultEnum getCode() {
        return res;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /* --------------- 公开的错误Code静态信息 -------------- */

    public ApiRes() {
        this.res = ResultEnum.Success;
        this.msg = "操作成功";
        this.data = null;
    }

    /* ------------------ 错误信息方法 ---------------------- */

    /**
     * 服务器内部错误
     *
     * @param <T> 类实例泛型
     * @return 服务器内部错误
     */
    public static <T> ApiRes<T> error() {
        return error(ResultEnum.ServerError);
    }

    /**
     * 服务器内部错误，错误信息
     *
     * @param msg 错误信息
     * @param <T> 类实例泛型
     * @return 服务器内部错误，错误信息
     */
    public static <T> ApiRes<T> error(String msg) {
        return error(ResultEnum.ServerError, msg);
    }

    /**
     * 返回对应错误编号的错误
     *
     * @param resEnum 错误枚举编号
     * @param <T>  类实例泛型
     * @return 对应错误编号的错误
     */
    public static <T> ApiRes<T> error(ResultEnum resEnum) {
        return error(resEnum, resEnum.getText());
    }

    /**
     * 返回对应错误编号的错误，相应的错误信息
     *
     * @param resEnum 错误枚举编号
     * @param msg  错误信息
     * @param <T>  类实例泛型
     * @return 错误编号对应的错误 和相关错误信息
     */
    public static <T> ApiRes<T> error(ResultEnum resEnum, String msg) {
        ApiRes<T> res = new ApiRes<>();
        res.res = resEnum;
        res.msg = msg;
        return res;
    }

    /* --------------------- 成功的方法 ---------------------- */

    /**
     * 返回成功的信息
     *
     * @param <T> 类实例泛型
     * @return 返回成功的信息
     */
    public static <T> ApiRes<T> ok() {
        return new ApiRes();
    }

    /**
     * 返回成功和对应的msg信息
     *
     * @param msg 成功的msg信息
     * @param <T> 类实例泛型
     * @return 返回成功信息和对应的msg信息
     */
    public static <T> ApiRes<T> ok(String msg) {
        ApiRes<T> res = new ApiRes<>();
        res.msg = msg;
        return res;
    }

    /**
     * @param obj 类实例
     * @param <T> 类实例泛型
     * @return 返回成功信息和对应的类实例信息
     */
    public static <T> ApiRes<T> ok(T obj) {
        ApiRes<T> res = new ApiRes<>();
        res.data = obj;
        return res;
    }

    /**
     * @param msg 成功信息
     * @param obj 类实例
     * @param <T> 类实例泛型
     * @return 返回成功信息和对应类实力和对应msg信息
     */
    public static <T> ApiRes<T> ok(String msg, T obj) {
        ApiRes<T> res = new ApiRes<>();
        res.msg = msg;
        res.data = obj;
        return res;
    }

    /* --------------- 额外的处理方法 ------------------ */

    /**
     * 将当前返回的信息转化为jsonRpc协议形式。
     *
     * @return jsonRpc协议形式的当前返回信息
     */
    public Map<String, Object> toJsonRpc() {
        Map<String, Object> jsonRpc = new HashMap<>();
        jsonRpc.put("jsonrpc", "2.0");
        jsonRpc.put("id", "");

        if (res == ResultEnum.Success) {
            jsonRpc.put("result", data);
        } else {
            Map<String, String> jsonRpcError = new HashMap<>();
            jsonRpcError.put("code", res.getRpcCode());
            jsonRpcError.put("message", msg.isEmpty() ? res.getEnText() : msg);

            jsonRpc.put("result", data);
        }
        return jsonRpc;
    }
}
