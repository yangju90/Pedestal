package indi.mat.work.project.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {
    // ID
    private Long id;

    // 逻辑删除 1 为删除 0 为正常
    private boolean deleted;

    // 插入记录的用户名
    private String opUser;

    // 插入记录的时间
    private Date opDate;

    // 最后一次修改的用户名
    private String lastOpUser;

    // 最后一次修改的时间
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
