package org.litu.app.constant;

import org.litu.core.base.BaseConstant;
import org.litu.util.common.SysMsgUtil;
import org.litu.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统常量
 */
@Component
public class SysContant extends BaseConstant {

    /*-----------------------------系统业务相关内容------------------------------------*/






    /*-----------------------------系统框架相关内容------------------------------------*/

    // 角色岗位的枚举
    public static final int ROLETYPE_ROLE = 1;
    public static final int ROLETYPE_STATION = 2;

    // 菜单类型的静态变量，需要与字典值信息对应起来
    public static final String MENUTYPE_MODULE = "1";
    public static final String MENUTYPE_FUNCTION = "2";
    public static final String MENUTYPE_BUTTON = "3";

    // session中存储的菜单列表信息
    public static final String SESSION_MENU = "menu-list";

    /*-----------------------------系统配置相关内容------------------------------------*/

    // 日志输出时，当前系统的标识
    public static String CURRENT_SYSTEM_CODE = "";

    @Value("${ltsystem.sysmsg.code}")
    public void setCurrentSystemCode(String systemCode) {
        SysContant.CURRENT_SYSTEM_CODE = systemCode;
    }

    //文件存储目录,结尾需要带这/
    public static String FILE_BASE_PATH = "/file/";

    @Value("${ltsystem.file.basePath}")
    public void setFileBasePath(String fileBasePath) {
        String basePath = "";
        if (FileUtil.isAbsolutePath(fileBasePath)) {
            basePath = fileBasePath;
        } else {
            String rootPath = SysMsgUtil.JarPath();
            basePath = rootPath + "/" + fileBasePath;
        }
        // 保证文件目录结尾有斜线结束
        FILE_BASE_PATH = basePath + (basePath.endsWith("/") ? "" : "/");
    }

    //文件存储目录
    public static String FILE_PATH_PATTERN = "YYYY-MM/";

    @Value("${ltsystem.file.pattern}")
    public void setFilePathPattern(String filePathPattern) {
        FILE_PATH_PATTERN = filePathPattern;
    }
}
