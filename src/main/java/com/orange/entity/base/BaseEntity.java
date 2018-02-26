package com.orange.entity.base;

import java.util.Date;

/**
 * @author: GLGGAG
 * @Date: 2017/08/22/上午 10:21
 * @Description:
 */
public class BaseEntity {

    private Integer id;

    private Date updateTime;

    private Date createTime;

    /** 版本号  在插件中实现乐观锁 **/
    private Integer version;

    private Boolean  deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
