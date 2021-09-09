package ${package.ServiceImpl};


import ${package.Entity}.${entity};
import ${package.Query}.${entity}Query;
import ${package.Form}.${entity}Form;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${entity},${entity}Form,${entity}Query> implements ${table.serviceName} {

   private static final Logger logger = LoggerFactory.getLogger(${table.serviceImplName}.class);

   @Autowired
   private ${entity}Mapper mapper;

    @Override
    protected BaseMapper<${entity}> mapper() {
        return mapper;
    }

    @Override
    protected ${entity} model() {
        return new ${entity}();
    }

}
</#if>
