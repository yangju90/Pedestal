package indi.mat.work.project.helper;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;


public class CoderGenerate {

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
        String codePath = projectPath+"\\indi\\mat\\work\\project";

        gc.setAuthor(author);
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("indi.mat.work.project");
        mpg.setPackageInfo(pc);
        // 配置模版
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setSuperEntityClass("indi.mat.work.project.model.BaseModel");
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("indi.mat.work.project.controller.BaseController");
        strategy.setSuperServiceClass("indi.mat.work.project.service.base.IBaseService");
        strategy.setSuperServiceImplClass("indi.mat.work.project.service.base.BaseService");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id","deleted","op_user","op_date","last_op_user","last_op_date");
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setTablePrefix("indi_");
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
