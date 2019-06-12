package com.physical.app.bean;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/6/10
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class MemberCardVo {

    /**
     * id : 3
     * lastVer : 0
     * isValid : 1
     * createTime : 1560265135000
     * opTime : 1560265135000
     * machineCode : b0:f1:ec:c9:89:74
     * createPerson : jjj
     * updatePerson : jjj
     * lampLight : null
     * seedlingLight : null
     * superaudible : null
     * ultrashortWave : null
     * waveGuide : null
     * magneticIntensity : null
     * totalTime : 0
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
     * memberId : 7
     * memberName : tb
     * commentType : null
     * comment : null
     * medicineInfoList : [{"id":202,"code":"130","typeCode":"seedling","name":"牛蒡苗","num":7},{"id":207,"code":"180","typeCode":"seedling","name":"沙参苗","num":9}]
     */

    public int id;
    public int lastVer;
    public int isValid;
    public long createTime;
    public long opTime;
    public String machineCode;
    public String createPerson;
    public String updatePerson;
    public Object lampLight;
    public Object seedlingLight;
    public Object superaudible;
    public Object ultrashortWave;
    public Object waveGuide;
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
         * id : 202
         * code : 130
         * typeCode : seedling
         * name : 牛蒡苗
         * num : 7
         */

        public int id;
        public String code;
        public String typeCode;
        public String name;
        public int num;
    }
}
