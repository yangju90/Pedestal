package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import indi.mat.work.employee.dto.response.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import  ${package.Service}.I${entity}Service;
import ${package.Entity}.${entity};
import ${package.Query}.${entity}Query;
import ${package.Form}.${entity}Form;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>

</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
/**
 * <p>
 * ${table.comment!} ?????
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/api<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private I${entity}Service service;

    @GetMapping("{id}")
    public Response<${entity}> getById(@PathVariable("id") Long id ){
        return Response.SUCCESS(service.selectById(id));
    }

    @GetMapping
    public Response<IPage<${entity}>> getPage(${entity}Query query){
        return Response.SUCCESS(service.queryPage(query));
    }

    @PutMapping
    public Response<Long> add(@RequestBody  @Valid ${entity}Form form){
        return Response.SUCCESS(service.insert(form));
    }

    @PostMapping
    public Response<Integer> update(@RequestBody @Valid ${entity}Form form){
        return Response.SUCCESS(service.updateById(form));
    }

    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id ){
         return Response.SUCCESS(service.deleteById(id));
    }


}
</#if>
