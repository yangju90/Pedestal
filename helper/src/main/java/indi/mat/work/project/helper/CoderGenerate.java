package com.mat.work;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;


public class CodeGenerator {

    static String author = "Mat";

    static String moduleName = "system";

    static String  [] tableName = {"system_favorites_info"};




    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir")+"\\code-generator\\code";
        gc.setOutputDir(projectPath + "/src/main/java");
        deleteFile(new File(projectPath));
        String codePath = projectPath+"\\com\\mat\\work";

        gc.setAuthor(author);
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://local:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("com.mat.work");
        mpg.setPackageInfo(pc);
        // 配置模版
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setSuperEntityClass("com.mat.work.model.BaseModel");
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("com.mat.work.controller.BaseController");
        strategy.setSuperServiceClass("com.mat.work.service.base.IBaseService");
        strategy.setSuperServiceImplClass("com.mat.work.service.base.BaseService");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id","deleted","in_user","in_date","last_edit_user","last_edit_date");
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setTablePrefix("ns_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new NsFreemarkerTemplateEngine(codePath));
        mpg.execute();
    }


    public static boolean deleteFile( File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }
}
