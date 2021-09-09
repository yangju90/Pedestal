package ${package.Form};

import ${package.BaseForm};
import ${package.Entity}.${entity};
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * ${table.comment!} Form
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entity}Form extends BaseForm {



    public  ${entity} model(){
        ${entity} model = new ${entity}();
        BeanUtils.copyProperties(this,model);
        return model;
    }
}

