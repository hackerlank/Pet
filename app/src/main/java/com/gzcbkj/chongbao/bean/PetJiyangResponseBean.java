package com.gzcbkj.chongbao.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huangzhifeng on 2018/3/13.
 */

public class PetJiyangResponseBean extends ResponseBean {

    private ArrayList<PetBase> petBaseList;
    private ArrayList<BaseService> baseServiceList;
    private ArrayList<BaseConfigure> baseConfigureList;
    private ArrayList<BaseRoom> baseRoomList;

    public ArrayList<PetBase> getPetBaseList() {
        return petBaseList;
    }

    public void setPetBaseList(ArrayList<PetBase> petBaseList) {
        this.petBaseList = petBaseList;
    }

    public ArrayList<BaseService> getBaseServiceList() {
        return baseServiceList;
    }

    public void setBaseServiceList(ArrayList<BaseService> baseServiceList) {
        this.baseServiceList = baseServiceList;
    }

    public ArrayList<BaseConfigure> getBaseConfigureList() {
        return baseConfigureList;
    }

    public void setBaseConfigureList(ArrayList<BaseConfigure> baseConfigureList) {
        this.baseConfigureList = baseConfigureList;
    }

    public ArrayList<BaseRoom> getBaseRoomList() {
        return baseRoomList;
    }

    public void setBaseRoomList(ArrayList<BaseRoom> baseRoomList) {
        this.baseRoomList = baseRoomList;
    }

    public class BaseRoom implements Serializable {
        private long id;
        private String baseRoomName;
        private String baseRoomPrice;
        private String baseRoomSize;
        private String createtime;
        private String updatetime;
        private String remake;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getBaseRoomName() {
            return baseRoomName;
        }

        public void setBaseRoomName(String baseRoomName) {
            this.baseRoomName = baseRoomName;
        }

        public String getBaseRoomPrice() {
            return baseRoomPrice;
        }

        public void setBaseRoomPrice(String baseRoomPrice) {
            this.baseRoomPrice = baseRoomPrice;
        }

        public String getBaseRoomSize() {
            return baseRoomSize;
        }

        public void setBaseRoomSize(String baseRoomSize) {
            this.baseRoomSize = baseRoomSize;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getRemake() {
            return remake;
        }

        public void setRemake(String remake) {
            this.remake = remake;
        }
    }

    public class BaseConfigure implements Serializable {
        private long id;
        private String configureName;
        private String updatetime;
        private String createtime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getConfigureName() {
            return configureName;
        }

        public void setConfigureName(String configureName) {
            this.configureName = configureName;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }

    public class BaseService implements Serializable{
        private long id;
        private String baseServiceName;
        private String baseServicePrice;
        private String createtime;
        private String updatetime;
        private String remake;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getBaseServiceName() {
            return baseServiceName;
        }

        public void setBaseServiceName(String baseServiceName) {
            this.baseServiceName = baseServiceName;
        }

        public String getBaseServicePrice() {
            return baseServicePrice;
        }

        public void setBaseServicePrice(String baseServicePrice) {
            this.baseServicePrice = baseServicePrice;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getRemake() {
            return remake;
        }

        public void setRemake(String remake) {
            this.remake = remake;
        }
    }

    public class PetBase implements Serializable{
        private String adoptPetPic;
        private String createtime;
        private long id;
        private String petBaseAddress;
        private String petBaseLat;
        private String petBaseLng;
        private String petBaseName;
        private String petBasePic;
        private String remake;
        private String updatetime;

        public String getAdoptPetPic() {
            return adoptPetPic;
        }

        public void setAdoptPetPic(String adoptPetPic) {
            this.adoptPetPic = adoptPetPic;
        }

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

        public String getPetBaseAddress() {
            return petBaseAddress;
        }

        public void setPetBaseAddress(String petBaseAddress) {
            this.petBaseAddress = petBaseAddress;
        }

        public String getPetBaseLat() {
            return petBaseLat;
        }

        public void setPetBaseLat(String petBaseLat) {
            this.petBaseLat = petBaseLat;
        }

        public String getPetBaseLng() {
            return petBaseLng;
        }

        public void setPetBaseLng(String petBaseLng) {
            this.petBaseLng = petBaseLng;
        }

        public String getPetBaseName() {
            return petBaseName;
        }

        public void setPetBaseName(String petBaseName) {
            this.petBaseName = petBaseName;
        }

        public String getPetBasePic() {
            return petBasePic;
        }

        public void setPetBasePic(String petBasePic) {
            this.petBasePic = petBasePic;
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
}
