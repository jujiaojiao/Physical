package com.physical.app.bean;

import java.io.Serializable;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/19
 * 描述：会员信息管理
 */
public class MemberManageBean implements Serializable{

    /**
     * id : 4
     * lastVer : 0
     * isValid : 1
     * createTime : 1559357736000
     * opTime : 1559357736000
     * machineCode : null
     * createPerson : jjj
     * updatePerson : jjj
     * name : zz
     * nickName : null
     * mobile : 18689466314
     * idCard : 610321199309141133
     * sex : 1
     * birthday : 1465488000000
     * height : 178.0
     * weight : 66.0
     * firstTime : 1488988800000
     * vip : true
     * vipTimes : 2
     * usedTime : 1
     * userId : null
     * totalMoney : 100.0
     * medicalHistoryList : null
     */

    public int id;
    public int lastVer;
    public int isValid;
    public long createTime;
    public long opTime;
    public Object machineCode;
    public String createPerson;
    public String updatePerson;
    public String name;
    public Object nickName;
    public String mobile;
    public String idCard;
    public int sex;
    public long birthday;
    public double height;
    public double weight;
    public long firstTime;
    public boolean vip;
    public int vipTimes;
    public int usedTime;
    public Object userId;
    public double totalMoney;
    public Object medicalHistoryList;
}
