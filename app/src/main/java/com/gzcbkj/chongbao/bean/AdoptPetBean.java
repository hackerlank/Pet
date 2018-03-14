package com.gzcbkj.chongbao.bean;

import java.io.Serializable;

/**
 * Created by huangzhifeng on 2018/3/13.
 */

public class AdoptPetBean implements Serializable {

    private long id;
    private String createtime;
    private String petAge;
    private String petName;
    private int petSex;
    private String petStatus;
    private int petType;
    private String petTypeName;
    private int petVariety;
    private String petVarietyNmae;
    private String remake;
    private String updatetime;
    private String petHeadUrl;

    public String getPetHeadUrl() {
        return petHeadUrl;
    }

    public void setPetHeadUrl(String petHeadUrl) {
        this.petHeadUrl = petHeadUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetSex() {
        return petSex;
    }

    public void setPetSex(int petSex) {
        this.petSex = petSex;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }

    public int getPetType() {
        return petType;
    }

    public void setPetType(int petType) {
        this.petType = petType;
    }

    public String getPetTypeName() {
        return petTypeName;
    }

    public void setPetTypeName(String petTypeName) {
        this.petTypeName = petTypeName;
    }

    public int getPetVariety() {
        return petVariety;
    }

    public void setPetVariety(int petVariety) {
        this.petVariety = petVariety;
    }

    public String getPetVarietyNmae() {
        return petVarietyNmae;
    }

    public void setPetVarietyNmae(String petVarietyNmae) {
        this.petVarietyNmae = petVarietyNmae;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
