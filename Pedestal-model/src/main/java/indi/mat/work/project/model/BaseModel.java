package indi.mat.work.project.model;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {
    // ID
    @TableId
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    // 逻辑删除 1 为删除 0 为正常
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private boolean deleted = false;

    // 插入记录的用户名
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String opUser;

    // 插入记录的时间
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date opDate;

    // 最后一次修改的用户名
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String lastOpUser;

    // 最后一次修改的时间
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date lastOpDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getLastOpUser() {
        return lastOpUser;
    }

    public void setLastOpUser(String lastOpUser) {
        this.lastOpUser = lastOpUser;
    }

    public Date getLastOpDate() {
        return lastOpDate;
    }

    public void setLastOpDate(Date lastOpDate) {
        this.lastOpDate = lastOpDate;
    }
}
