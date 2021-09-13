package indi.mat.work.project.helper;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Mat
 * @version $Id: ${NAME}, v 0.1 ${YEAR}-${MONTH}-${DAY} ${TIME} Yang
 */
public class NsFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    private String  codePath;

    public NsFreemarkerTemplateEngine(String codePath) {
        this.codePath = codePath;
    }

    @Override
    public AbstractTemplateEngine batchOutput() {

        try {
            List<TableInfo> tableInfoList = this.getConfigBuilder().getTableInfoList();
            Iterator var2 = tableInfoList.iterator();

            while(var2.hasNext()) {
                TableInfo tableInfo = (TableInfo)var2.next();
                Map<String, Object> objectMap = this.getObjectMap(tableInfo);
                Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();
                TemplateConfig template = this.getConfigBuilder().getTemplate();
                setFormAndQuery(objectMap,pathInfo);

                String entityName = tableInfo.getEntityName();
                String controllerFile;
                if (null != entityName && null != pathInfo.get("entity_path")) {
                    controllerFile = String.format((String)pathInfo.get("entity_path") + File.separator + "%s" + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(FileType.ENTITY, controllerFile)) {
                        this.writerFile(objectMap, this.templateFilePath(template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())), controllerFile);
                    }
                }

                if (null != tableInfo.getMapperName() && null != pathInfo.get("mapper_path")) {
                    controllerFile = String.format((String)pathInfo.get("mapper_path") + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(FileType.MAPPER, controllerFile)) {
                        this.writerFile(objectMap, this.templateFilePath(template.getMapper()), controllerFile);
                    }
                }

                if (null != tableInfo.getXmlName() && null != pathInfo.get("xml_path")) {
                    controllerFile = String.format((String)pathInfo.get("xml_path") + File.separator + tableInfo.getXmlName() + ".xml", entityName);
                    if (this.isCreate(FileType.XML, controllerFile)) {
                        this.writerFile(objectMap, this.templateFilePath(template.getXml()), controllerFile);
                    }
                }

                if (null != tableInfo.getServiceName() && null != pathInfo.get("service_path")) {
                    controllerFile = String.format((String)pathInfo.get("service_path") + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(FileType.SERVICE, controllerFile)) {
                        this.writerFile(objectMap, this.templateFilePath(template.getService()), controllerFile);
                    }
                }

                if (null != tableInfo.getServiceImplName() && null != pathInfo.get("service_impl_path")) {
                    controllerFile = String.format((String)pathInfo.get("service_impl_path") + File.separator + tableInfo.getServiceImplName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(FileType.SERVICE_IMPL, controllerFile)) {
                        this.writerFile(objectMap, this.templateFilePath(template.getServiceImpl()), controllerFile);
                    }
                }

                if (null != tableInfo.getControllerName() && null != pathInfo.get("controller_path")) {
                    controllerFile = String.format((String)pathInfo.get("controller_path") + File.separator + tableInfo.getControllerName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(FileType.CONTROLLER, controllerFile)) {
                        this.writerFile(objectMap, this.templateFilePath(template.getController()), controllerFile);
                    }
                }

                if (null != entityName && null != pathInfo.get("query_path")) {
                    controllerFile = String.format((String)pathInfo.get("query_path") + File.separator + "%sQuery" + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(FileType.ENTITY, controllerFile)) {
                        this.writerFile(objectMap, "/templates/query.java.ftl", controllerFile);
                    }
                }

                if (null != entityName && null != pathInfo.get("form_path")) {
                    controllerFile = String.format((String)pathInfo.get("form_path") + File.separator + "%sForm" + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(FileType.ENTITY, controllerFile)) {
                        this.writerFile(objectMap, "/templates/form.java.ftl", controllerFile);
                    }
                }

            }
        } catch (Exception var11) {
            logger.error("无法创建文件，请检查配置信息！", var11);
        }

        return this;
    }



    private  void setFormAndQuery(Map objectMap, Map<String, String> pathInfo){
        Map<String,Object> packageMap = (HashMap)objectMap.get("package");
        String moduleName = packageMap.get("ModuleName")+"";
        packageMap.put("BaseQuery","indi.mat.work.project.dto.request.BaseQuery");
        packageMap.put("BaseForm","indi.mat.work.project.dto.request.BaseForm");

        packageMap.put("Entity","indi.mat.work.project.model."+moduleName);
        packageMap.put("Mapper","indi.mat.work.project.persist."+moduleName);
        packageMap.put("ServiceImpl","indi.mat.work.project.service"+moduleName+".impl");
        packageMap.put("Service","indi.mat.work.project.service."+moduleName);
        packageMap.put("Controller","indi.mat.work.project.controller."+moduleName);
        packageMap.put("Query","indi.mat.work.project.dto.request."+moduleName+".query");
        packageMap.put("Form","indi.mat.work.project.dto.request."+moduleName+".form");

        pathInfo.put("mapper_path",codePath+"\\persist\\"+moduleName);
        pathInfo.put("entity_path",codePath+"\\model\\"+moduleName);
        pathInfo.put("controller_path",codePath+"\\controller\\"+moduleName);
        pathInfo.put("service_impl_path",codePath+"\\service\\"+moduleName+"\\impl");
        pathInfo.put("service_path",codePath+"\\service\\"+moduleName);
        pathInfo.put("query_path",codePath+"\\dto\\request\\"+moduleName+"\\query");
        pathInfo.put("form_path",codePath+"\\dto\\request\\"+moduleName+"\\form");
    }

    @Override
    public AbstractTemplateEngine mkdirs() {
        return this;
    }
}
