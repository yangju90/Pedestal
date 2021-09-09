package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Query}.${entity}Query;
import ${package.Form}.${entity}Form;
import ${superServiceClassPackage};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity},${entity}Form,${entity}Query> {

}
</#if>
