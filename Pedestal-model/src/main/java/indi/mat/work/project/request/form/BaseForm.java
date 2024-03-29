package indi.mat.work.project.request.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public abstract class BaseForm {

    /** 自增主键 */
    @Min(1)
    @Schema(description = "update operation  is required ; add operation please ignore")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
