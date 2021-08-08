package indi.mat.work.project.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {
    // ID
    private Long id;

    // 逻辑删除 1 为删除 0 为正常
    private boolean deleted;

    // 插入记录的用户名
    private String inUser;

    // 插入记录的时间
    private Date inDate;

    // 最后一次修改的用户名
    private String lastEditUser;

    // 最后一次修改的时间
    private Date lastEditDate;

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

    public String getInUser() {
        return inUser;
    }

    public void setInUser(String inUser) {
        this.inUser = inUser;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getLastEditUser() {
        return lastEditUser;
    }

    public void setLastEditUser(String lastEditUser) {
        this.lastEditUser = lastEditUser;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }
}
