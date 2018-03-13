package com.gzcbkj.chongbao.bean;

import java.io.Serializable;

/**
 * Created by huangzhifeng on 2018/3/13.
 */

public class PetVarietyBean implements Serializable {

    private String createtime;
    private long id;
    private long petType;
    private String petVariety;
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

    public long getPetType() {
        return petType;
    }

    public void setPetType(long petType) {
        this.petType = petType;
    }

    public String getPetVariety() {
        return petVariety;
    }

    public void setPetVariety(String petVariety) {
        this.petVariety = petVariety;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
