package org.litu.core;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mp代码生成器
 * 使用注意事项：  由于模板的缺陷，生成后的代码需要执行如下操作：
 * 1、 全局进行替换： $ {data ->  ${data
 */
public class MybatisPlusGenerator {
    private static String generatPath = "E:/Geanetate/code";
    private static String author = "ltgk";
    private static String systemCode = "HaierResearch";
    private static String sqlCreator = "admin";

    public static void generatorMoudleCode(GlobalConfig gc) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://47.94.198.207:3306/normalappdb?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT");
        dsc.setUsername("root");
        dsc.setPassword("qazwsx123");

        // 所有要进行生成的表和模块信息
        List<moduleConfig> moduleConfigs = new ArrayList<>();
        moduleConfigs.add(new moduleConfig("系统管理", "", new String[]{"Sys_DictItem", "Sys_Dict", "Sys_AccessToken", "Sys_DbBackup", "Sys_RoleMenu", "Sys_UserLogin", "Sys_User", "Sys_UserRole", "Sys_System", "Sys_Logs", "Sys_SystemVersion", "Sys_Menu", "Sys_Configs", "Sys_Files", "Sys_RoleOrganize", "Sys_Organize", "Sys_Role"}));

        for (moduleConfig module : moduleConfigs) {
            AutoGenerator mpg = new AutoGenerator();
            mpg.setGlobalConfig(gc);
            mpg.setDataSource(dsc);

            // 模板配置
            mpg.setTemplate(getTemplateConfig());

            // 策略配置
            StrategyConfig strategy = getStrategyConfig();
            strategy.setInclude(module.getTableNames()); //包含的表
            // strategy.setExclude(new String[]{"test"}); // 排除的表
            mpg.setStrategy(strategy);

            // 包配置
            mpg.setPackageInfo(getPackageConfig(module.getModuleCode()));

            // 自定义注入内容,可以在 VM 中使用 cfg.code 访问
            Map<String, Object> cfg = new HashMap<>();
            cfg.put("systemCode", systemCode);
            cfg.put("moduleCode", module.getModuleCode());
            cfg.put("moduleName", module.getModuleName());
            cfg.put("sqlCreator", sqlCreator);
            // todo 这里可以列自己所需要在界面使用的额外参数
            mpg.setCfg(getInjectionConfig(module.getModuleCode(), cfg));

            // 执行生成
            mpg.execute();
        }

        // 打开生成文件的目录。
        openDir(generatPath);
    }

    /**
     * 自定义模板配置, 默认在 mybatis-plus/src/main/resources/templates下
     *
     * @return
     */
    private static TemplateConfig getTemplateConfig() {
        TemplateConfig tcg = new TemplateConfig();
        tcg.setEntity("genTemplates/entity.java");
        tcg.setMapper("genTemplates/mapper.java");
        tcg.setService("genTemplates/service.java");
        tcg.setServiceImpl("genTemplates/serviceImpl.java");
        tcg.setController("genTemplates/controller.java");
        tcg.setXml("genTemplates/mapper.xml");
        return tcg;
    }

    /**
     * 生成的包配置
     *
     * @return
     */
    private static PackageConfig getPackageConfig(String moduleCode) {
        PackageConfig pc = new PackageConfig();
        pc.setParent("org.litu.app");
        pc.setMapper("dao");
        pc.setEntity("entity");
        pc.setXml("dao.xml");

        Map<String, String> outputPath = new HashMap<>();
        outputPath.put(ConstVal.ENTITY_PATH, generatPath + "/org/litu/app/entity");
        outputPath.put(ConstVal.MAPPER_PATH, generatPath + "/org/litu/app/dao");
        outputPath.put(ConstVal.XML_PATH, generatPath + "/org/litu/app/dao/xml");
        outputPath.put(ConstVal.SERVICE_PATH, generatPath + "/org/litu/app/service");
        outputPath.put(ConstVal.SERVICE_IMPL_PATH, generatPath + "/org/litu/app/service/impl");
        outputPath.put(ConstVal.CONTROLLER_PATH, generatPath + "/org/litu/app/controller/" + moduleCode);
        pc.setPathInfo(outputPath);

        return pc;
    }

    /**
     * 生成的策略配置
     *
     * @return
     */
    private static StrategyConfig getStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setSuperEntityClass("org.litu.base.entity.BaseEntity");
        strategy.setSuperEntityColumns("f_id");
        strategy.setSuperMapperClass("org.litu.base.dao.BaseMapper");
        strategy.setSuperServiceClass("org.litu.base.service.IBaseService");
        strategy.setSuperServiceImplClass("org.litu.base.service.impl.BaseServiceImpl");
        strategy.setSuperControllerClass("org.litu.base.controller.BaseFormController");
        return strategy;
    }

    /**
     * 生成用的额外注入配置，自定义变量，路径等
     *
     * @return
     */
    private static InjectionConfig getInjectionConfig(String moduleCode, Map<String, Object> cfg) {
        // 自定义 文件生成
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("genTemplates/index.ftl.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return generatPath + "/html/" + moduleCode + "/" + tableInfo.getEntityPath() + "/index.ftl";
            }
        });
        focList.add(new FileOutConfig("genTemplates/form.ftl.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return generatPath + "/html/" + moduleCode + "/" + tableInfo.getEntityPath() + "/form.ftl";
            }
        });
        focList.add(new FileOutConfig("genTemplates/view.ftl.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return generatPath + "/html/" + moduleCode + "/" + tableInfo.getEntityPath() + "/view.ftl";
            }
        });
        focList.add(new FileOutConfig("genTemplates/menu.sql.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return generatPath + "/html/" + moduleCode + "/" + tableInfo.getEntityPath() + "/menu.sql";
            }
        });

        InjectionConfig icfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // 注入自定义配置，可以在 VM 中使用 cfg.systemCode 设置的值
                this.setMap(cfg);
            }
        };

        icfg.setFileOutConfigList(focList);
        return icfg;
    }

    /**
     * 打开生成目录
     *
     * @param outDir
     * @return
     */
    private static void openDir(String outDir) {
        try {
            String osName = System.getProperty("os.name");
            if (osName != null) {
                if (osName.contains("Mac")) {
                    Runtime.getRuntime().exec("open " + outDir);
                } else if (osName.contains("Windows")) {
                    Runtime.getRuntime().exec("cmd /c start " + outDir);
                } else {
                    System.out.printf("文件输出目录:" + outDir);
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * <p>
     * 代码生成
     * </p>
     */
    public static void main(String[] args) {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(generatPath);
        gc.setSwagger2(true);
        gc.setFileOverride(true);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);//xml中开启二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setIdType(IdType.ID_WORKER_STR);
        gc.setAuthor(author);

        generatorMoudleCode(gc);
    }
}

/**
 * 模块信息实体
 */
class moduleConfig {
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 模块编码
     */
    private String moduleCode;
    /**
     * 模块下所有的表
     */
    private String[] tableNames;

    public moduleConfig(String moduleName, String moduleCode, String[] tableNames) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.tableNames = tableNames;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }
}