package com.physical.app.bean;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/22
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 理疗次数
 */

public class TimeBean {

    /**
     * id : 9
     * lastVer : 0
     * isValid : 1
     * createTime : 1560347757000
     * opTime : 1560347757000
     * machineCode : b0:f1:ec:c9:89:74
     * createPerson : jjj
     * updatePerson : jjj
     * lampLight : 1
     * seedlingLight : 1
     * superaudible : 1
     * ultrashortWave : 1
     * waveGuide : 1
     * magneticIntensity : null
     * totalTime : 10
     * status : 0
     * beginTime : null
     * endTime : null
     * lastUpdateTime : null
     * usedTime : 0
     * userId : 10005
     * userName : jjj
     * payType : 0
     * address : null
     * machineId : 0
     * memberId : 4
     * memberName : zz
     * commentType : null
     * comment : null
     * medicineInfoList : [{"id":200,"code":"110","typeCode":"seedling","name":"豌豆苗","num":4},{"id":204,"code":"150","typeCode":"seedling","name":"仙人球","num":7}]
     */

    public int id;
    public int lastVer;
    public int isValid;
    public long createTime;
    public long opTime;
    public String machineCode;
    public String createPerson;
    public String updatePerson;
    public int lampLight;
    public int seedlingLight;
    public int superaudible;
    public int ultrashortWave;
    public int waveGuide;
    public Object magneticIntensity;
    public int totalTime;
    public int status;
    public Object beginTime;
    public Object endTime;
    public Object lastUpdateTime;
    public int usedTime;
    public int userId;
    public String userName;
    public int payType;
    public Object address;
    public int machineId;
    public int memberId;
    public String memberName;
    public Object commentType;
    public Object comment;
    public List<MedicineInfoListBean> medicineInfoList;

    public static class MedicineInfoListBean {
        /**
         * id : 200
         * code : 110
         * typeCode : seedling
         * name : 豌豆苗
         * num : 4
         */

        public int id;
        public String code;
        public String typeCode;
        public String name;
        public int num;
    }
}
