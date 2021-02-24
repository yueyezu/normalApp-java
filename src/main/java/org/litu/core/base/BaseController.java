package org.litu.core.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.SessionListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * controller的父类
 */
public abstract class BaseController extends SessionListenerAdapter implements SessionListener {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /* ***************** start 返回request参数处理的方法 **************** */

    /**
     * 获取request中的 参数
     *
     * @param param 参数
     * @return request中的 参数
     */
    protected String request(String param) {
        return request.getParameter(param);
    }

    /**
     * 获取request中的 数组类型参数
     *
     * @param param 参数
     * @return request中的 数组类型参数
     */
    protected String[] requestArr(String param) {
        return request.getParameterValues(param);
    }

    /**
     * 获取请求参数的所有信息
     *
     * @return 请求参数的所有信息
     */
    protected Map<String, String> requestParams() {
        Map<String, String[]> realParams = request.getParameterMap();

        Map<String, String> allParams = new HashMap<>();
        for (Map.Entry<String, String[]> param : realParams.entrySet()) {
            String val;
            if (param.getValue().length > 1) {
                val = StringUtils.join(",", param.getValue());
            } else {
                val = param.getValue()[0];
            }
            allParams.put(param.getKey(), val);
        }
        return allParams;
    }

    /* ***************** end 返回request参数处理的方法 **************** */

    // /*
    // * 获取泛型的实际类型
    // *
    // * @return
    // */
    // protected Class<?>[] getTypes() {
    // ParameterizedType parameterizedType = (ParameterizedType)
    // this.getClass().getGenericSuperclass();
    // Type[] types = parameterizedType.getActualTypeArguments();
    // Class<?>[] result = new Class<?>[2];
    // result[0] = (Class<?>) types[0];
    // result[1] = (Class<?>) types[1];
    // return result;
    // }

    /* **************** start 共通配置的部分内容 ******************* */

    /**
     * 请求参数，返回参数处理 防止XSS攻击 Date类型转换为时间戳
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date与时间戳互转

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    if (text.length() > 10) {
                        setValue(StringUtils.isBlank(text) ? null : DateUtils.parseDate(text, "yyyy-MM-dd HH:mm:ss"));
                    } else {
                        setValue(StringUtils.isBlank(text) ? null : DateUtils.parseDate(text, "yyyy-MM-dd"));
                    }
                } catch (ParseException e) {
                    throw new IllegalArgumentException("日期格式错误!");
                }
            }

            @Override
            public String getAsText() {
                Date value = (Date) getValue();
                return value == null ? "" : DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss");
            }
        });
    }

    /* **************** end 共通配置的部分内容 ********************* */
}
