package com.gzcbkj.chongbao.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by huangzhifeng on 2018/3/14.
 */

public class MyPetBean extends BaseBean {

    private String createtime;
    private String ownPetBirth;
    private String ownPetHeadurl;
    private String ownPetInterest;
    private String ownPetName;
    private String ownPetSex;
    private String ownPetSterilization;
    private String ownPetType;
    private String ownPetVariety;
    private String ownPetWeight;
    private String ownUserId;
    private long id;
    private String updatetime;
    private String petVarietyName;
    private String typeName;
    private String petAge;

    public String getPetAge() {
        if(TextUtils.isEmpty(petAge) && !TextUtils.isEmpty(ownPetBirth)){
            Calendar calendar=Calendar.getInstance();
            int currentYear=calendar.get(Calendar.YEAR);
            int year=Integer.parseInt(ownPetBirth.split("-")[0]);
            petAge=String.valueOf(currentYear-year);
        }
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetVarietyName() {
        return petVarietyName;
    }

    public void setPetVarietyName(String petVarietyName) {
        this.petVarietyName = petVarietyName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getOwnPetBirth() {
        return ownPetBirth;
    }

    public void setOwnPetBirth(String ownPetBirth) {
        this.ownPetBirth = ownPetBirth;
    }

    public String getOwnPetHeadurl() {
        return ownPetHeadurl;
    }

    public void setOwnPetHeadurl(String ownPetHeadurl) {
        this.ownPetHeadurl = ownPetHeadurl;
    }

    public String getOwnPetInterest() {
        return ownPetInterest;
    }

    public void setOwnPetInterest(String ownPetInterest) {
        this.ownPetInterest = ownPetInterest;
    }

    public String getOwnPetName() {
        return ownPetName;
    }

    public void setOwnPetName(String ownPetName) {
        this.ownPetName = ownPetName;
    }

    public String getOwnPetSex() {
        return ownPetSex;
    }

    public void setOwnPetSex(String ownPetSex) {
        this.ownPetSex = ownPetSex;
    }

    public String getOwnPetSterilization() {
        return ownPetSterilization;
    }

    public void setOwnPetSterilization(String ownPetSterilization) {
        this.ownPetSterilization = ownPetSterilization;
    }

    public String getOwnPetType() {
        return ownPetType;
    }

    public void setOwnPetType(String ownPetType) {
        this.ownPetType = ownPetType;
    }

    public String getOwnPetVariety() {
        return ownPetVariety;
    }

    public void setOwnPetVariety(String ownPetVariety) {
        this.ownPetVariety = ownPetVariety;
    }

    public String getOwnPetWeight() {
        return ownPetWeight;
    }

    public void setOwnPetWeight(String ownPetWeight) {
        this.ownPetWeight = ownPetWeight;
    }

    public String getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(String ownUserId) {
        this.ownUserId = ownUserId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
