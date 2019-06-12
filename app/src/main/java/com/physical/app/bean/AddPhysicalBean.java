package com.physical.app.bean;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/12
 * 描述：
 */
public class AddPhysicalBean {

    /**
     * id : null
     * lastVer : null
     * isValid : null
     * createTime : 1560345039893
     * opTime : 1560345039893
     * machineCode : b0:f1:ec:c9:89:74
     * createPerson : jjj
     * updatePerson : jjj
     * lampLight : 6
     * seedlingLight : 4
     * superaudible : 3
     * ultrashortWave : 4
     * magneticIntensity : 2
     * totalTime : 30
     * status : 0
     * beginTime : null
     * endTime : null
     * lastUpdateTime : null
     * usedTime : null
     * userId : 10005
     * userName : jjj
     * payType : 0
     * address : null
     * machineId : 0
     * memberId : 4
     * memberName : zz
     * commentType : null
     * comment : null
     * medicineInfoList : [{},{},{}]
     */

    public int id;
    public Object lastVer;
    public Object isValid;
    public long createTime;
    public long opTime;
    public String machineCode;
    public String createPerson;
    public String updatePerson;
    public int lampLight;
    public int seedlingLight;
    public int superaudible;
    public int ultrashortWave;
    public int magneticIntensity;
    public int totalTime;
    public int status;
    public Object beginTime;
    public Object endTime;
    public Object lastUpdateTime;
    public Object usedTime;
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
    }
}
