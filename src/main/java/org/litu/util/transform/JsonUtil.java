package org.litu.util.transform;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * json转化类，
 * 目前采用 jackjson
 * 其实json处理，单纯的使用json工具类也可以，这里列出util类，主要是方便后续进行json工具类切换时，减少业务代码的影响。
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * javaBean、列表数组转换为json字符串
     */
    public static String pojo2json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Object to Json Error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * javaBean、列表数组转换为json字符串,忽略空值
     */
    public static String pojo2jsonIgnoreNull(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Object to Json Error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * json 转JavaBean
     */
    public static <T> T json2pojo(String jsonString, Class<T> clazz) {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("json to pojo error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为map
     */
    public static Map<String, Object> json2map(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.readValue(jsonString, Map.class);
        } catch (IOException e) {
            logger.error("json to map error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为map
     */
    public static <T> Map<String, T> json2map(String jsonString, Class<T> clazz) {
        Map<String, Map<String, Object>> map = null;
        try {
            map = objectMapper.readValue(jsonString, new TypeReference<Map<String, T>>() {
            });
        } catch (IOException e) {
            logger.error("json to map error", e);
            throw new RuntimeException(e);
        }
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), objectMapper.convertValue(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * 深度转换json成map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> json2mapDeeply(String json) {
        try {
            return json2MapRecursion(json, objectMapper);
        } catch (IOException e) {
            logger.error("json to map error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 与javaBean json数组字符串转换为列表
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            JavaType javaType = objMapper.getTypeFactory().constructParametricType(ArrayUtils.class, clazz);
            List<T> list = (List<T>) objMapper.readValue(jsonArrayStr, javaType);
            return list;
        } catch (IOException e) {
            logger.error("json convert to Object Error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * map 转json
     *
     * @param map
     * @return
     */
    public static String map2json(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            logger.error("map to json error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json解析成list，如果list内部的元素存在jsonString，继续解析
     *
     * @param json
     * @param mapper 解析工具
     * @return
     * @throws Exception
     */
    private static List<Object> json2ListRecursion(String json, ObjectMapper mapper) throws IOException {
        if (json == null) {
            return null;
        }

        List<Object> list = mapper.readValue(json, List.class);

        for (Object obj : list) {
            if (obj != null && obj instanceof String) {
                String str = (String) obj;
                if (str.startsWith("[")) {
                    obj = json2ListRecursion(str, mapper);
                } else if (obj.toString().startsWith("{")) {
                    obj = json2MapRecursion(str, mapper);
                }
            }
        }

        return list;
    }

    /**
     * 把json解析成map，如果map内部的value存在jsonString，继续解析
     *
     * @param json
     * @param mapper
     * @return
     * @throws Exception
     */
    private static Map<String, Object> json2MapRecursion(String json, ObjectMapper mapper) throws IOException {
        if (json == null) {
            return null;
        }

        Map<String, Object> map = mapper.readValue(json, Map.class);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj != null && obj instanceof String) {
                String str = ((String) obj);

                if (str.startsWith("[")) {
                    List<?> list = json2ListRecursion(str, mapper);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = json2MapRecursion(str, mapper);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }

        return map;
    }
}
