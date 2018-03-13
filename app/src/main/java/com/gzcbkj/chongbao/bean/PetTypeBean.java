package com.gzcbkj.chongbao.bean;

import java.io.Serializable;

/**
 * Created by huangzhifeng on 2018/3/13.
 */

public class PetTypeBean implements Serializable {

    private String createtime;
    private long id;
    private String typeName;
    private String updatetime;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
