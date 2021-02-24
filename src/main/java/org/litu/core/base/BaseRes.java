package org.litu.core.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.litu.core.enums.ResultEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口通用返回数据类型
 * 返回字段包括： code、msg、data
 *
 * @author ltgk
 */
public class BaseRes<T> extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    @JsonIgnoreProperties
    private ResultEnum resCode;

    /* --------------- 公开的错误Code静态信息 -------------- */

    public BaseRes() {
        resCode = ResultEnum.Success;
        put("code", resCode.getCode());
        put("msg", resCode.getText());
    }

    /*--------- 失败的返回 ---------*/

    /**
     * @return 服务器内部错误，Internal error
     */
    public static BaseRes error() {
        return error(ResultEnum.ServerError, ResultEnum.ServerError.getText());
    }

    /**
     * @param msg 错误信息
     * @return 服务器内部错误，相关错误信息
     */
    public static BaseRes error(String msg) {
        return error(ResultEnum.ServerError, msg);
    }

    /**
     * 对应编码的错误
     *
     * @param code 错误编码
     * @return 错误编码和对应的错误信息
     */
    public static BaseRes error(ResultEnum code) {
        return error(code, code.getText());
    }

    /**
     * 返回错误的信息，
     * code需要在本实体类中进行确定。
     *
     * @param code 错误编码
     * @param msg  错误的描述信息
     * @return 错误的信息
     */
    public static <T> BaseRes error(ResultEnum code, T msg) {
        BaseRes res = new BaseRes();
        res.resCode = code;
        res.put("code", code.getCode());
        res.put("msg", msg);
        return res;
    }

    /*----------- 成功的返回 ------------*/

    /**
     * 执行成功的返回方法
     *
     * @return 返回提交成功
     */
    public static BaseRes ok() {
        return new BaseRes();
    }

    /**
     * 执行成功的返回方法
     *
     * @param msg 成功的信息
     * @return 返回提交成功和对应的信息
     */
    public static BaseRes ok(String msg) {
        BaseRes res = new BaseRes();
        res.put("msg", msg);
        return res;
    }

    /**
     * 执行成功的返回方法
     *
     * @param obj 传入的数据对象
     * @return 提交成功和相应的对象
     */
    public static <T> BaseRes<T> ok(T obj) {
        BaseRes res = new BaseRes();
        res.put("data", obj);
        return res;
    }

    /**
     * 执行成功的返回方法
     *
     * @param map 传入Map类型的数据
     * @return 提交成功和相应的对象
     */
    public static BaseRes ok(Map<String, Object> map) {
        BaseRes res = new BaseRes();
        res.putAll(map);
        return res;
    }

    /*------------ 处理方法 ------------*/

    /**
     * 执行分页查询成功的放回方法
     *
     * @param total 数据总条数
     * @param rows  数据的信息
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> BaseRes page(long total, List<T> rows) {
        BaseRes res = new BaseRes();
        res.put("total", total);
        res.put("rows", rows);
        return res;
    }

    /*------------ 处理方法 ------------*/

    /**
     * @param key   key值
     * @param value value值
     * @return 生成Map
     */
    @Override
    public BaseRes put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 将当前返回的信息转化为jsonrpc协议形式。
     *
     * @return jsonrpc协议形式的信息
     */
    public Map<String, Object> toJsonRpc() {
        Map<String, Object> jsonRpc = new HashMap<>();
        jsonRpc.put("jsonrpc", "2.0");
        jsonRpc.put("id", "0");

        String msg = get("msg").toString();
        if (resCode == ResultEnum.Success) {
            jsonRpc.put("result", get("data"));
        } else {
            Map<String, String> jsonRpcError = new HashMap<>();
            jsonRpcError.put("code", resCode.getRpcCode());
            jsonRpcError.put("message", msg.isEmpty() ? resCode.getEnText() : msg);
            jsonRpc.put("result", get("data"));
        }
        return jsonRpc;
    }
}
