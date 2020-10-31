package org.litu.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Util {
    /// <summary>
    /// 表示全局唯一标识符 (GUID)。
    /// </summary>
    /// <returns></returns>
    public static String GuId36() {
        return UUID.randomUUID().toString();
    }

    /// <summary>
    /// 表示全局唯一标识符 (GUID)。
    /// </summary>
    /// <returns></returns>
    public static String GuId32() {
        String uuid = UUID.randomUUID().toString();

        return uuid.replace("-", "");
    }

    /**
     * 自动生成编号 20100825114540985
     *
     * @return 生成后编号
     */
    public static String CreateNo() {
        int strRandom = (int) (Math.random() * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

        return dateFormat.format(new Date()) + strRandom;
    }
}
