package org.litu.util.net;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * url工具类 功能:对url处理功能
 */
public class URLUtil {
    /**
     * 对URL的中文进行编码
     *
     * @param url 来源字符串
     * @return 编码后的字符串
     */
    @SuppressWarnings("deprecation")
    public static String urlEncode(String url) {
        return java.net.URLEncoder.encode(url);
    }

    /**
     * 对URL的中文进行解码
     *
     * @param url 来源字符串
     * @return 解码后的字符串
     */
    @SuppressWarnings("deprecation")
    public static String urlDecode(String url) {
        return java.net.URLDecoder.decode(url);
    }

    /**
     * 把参数组成的MAP转化到对应的URL格式
     *
     * @param paramMap Map参数
     * @return url
     */
    public static String toURL(Map<?, ?> paramMap) {
        if (paramMap == null) {
            return "";
        }

        String url = "";
        for (Iterator<?> iter = paramMap.keySet().iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            String value = (paramMap.get(key)).toString();
            String param = key + "=" + value;
            url += param;
            url += "&";
        }

        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * 描述：和toUrl反向
     *
     * @param url 来源字符串
     * @return toUrl反向的url
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, String> toMap(String url) {
        if (url == null)
            return null;

        Map paramMap = new HashMap();
        String[] paramArray = url.split("&");
        for (int i = 0; i < paramArray.length; i++) {
            String[] param = paramArray[i].split("=");
            if (param.length == 2) {
                paramMap.put(param[0], param[1]);
            }
        }
        return paramMap;
    }

    /**
     * 描述：移出查询字串中的某查询参数
     *
     * @param queryString 查询字串
     * @param paramName   查询参数
     * @return 移出后的查询字符串
     */
    public static String removeQueryParam(String queryString, String paramName) {
        if (StringUtils.isEmpty(queryString)) {
            return "";
        }
        if (StringUtils.isEmpty(paramName)) {
            return queryString;
        }
        String key = paramName + "=";
        int pos = queryString.indexOf(paramName + "=");
        if (pos == -1) {
            return queryString;
        }
        String startStr = queryString.substring(0, pos);
        String endStr = queryString.substring(pos + key.length());
        pos = endStr.indexOf("&");
        endStr = pos == -1 ? "" : endStr.substring(pos + 1);

        String retStr = startStr + endStr;
        if (retStr.endsWith("&")) {
            retStr = retStr.substring(0, retStr.length() - 1);
        }
        return retStr;
    }

    public static void main(String[] args) {
        String url = URLUtil.urlEncode("你好");
        System.out.println(url);
        String url1 = URLUtil.urlDecode("https://www.baidu.com/s?rsv_idx=1&tn=79081068_oem_dg&wd=%E8%A7%A3%E6%94%BE%E5%86%9B%E4%B8%9C%E6%B5%B7%E6%BC%94%E4%B9%A0&usm=1&ie=utf-8&rsv_cq=%E5%8F%B8%E6%9C%BA%E5%BC%83%E5%8D%95%E9%80%81%E5%AD%95%E5%A6%87&rsv_dl=0_right_fyb_pchot_20811");
        System.out.println(url1);
        Map<String, String> map = new HashMap<>();
        map.put("wwww.baidu.com", "你好");
        String url2 = URLUtil.toURL(map);
        System.out.println(url2);
        Map<String, String> map1 = URLUtil.toMap(url2);
        System.out.println(map1);
    }
}
