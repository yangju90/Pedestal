package indi.mat.work.project.request.form;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Size;

public abstract class BaseForm<T> {

    @NonNull
    @Size
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
