package com.physical.app.bean;

import java.util.Date;
import java.util.List;

/**
 * 会员病例
 */
public class MemberCaseVo {

    /**
     * 灯光
     */
    public String lampLight;

    /**
     * 苗光
     */
    public String seedlingLight;

    /**
     * 超声波
     */
    public String superaudible;

    /**
     * 短超波
     */
    public String ultrashortWave;

    /**
     * 磁场强度
     */
    public String  magneticIntensity;

    /**
     * 总疗养时间
     */
    public String totalTime;
    /**
     * 波导管
     */
    public String waveGuide;
    /**
     * 0-疗养中,1-暂停,2-完成
     */
    public int status;

    /**
     * 开始时间
     */
    public String beginTime;

    /**
     * 结束时间
     */
    public String endTime;

    /**
     * 上一次更新时间
     */
    public String lastUpdateTime;

    /**
     * 已疗养时间
     */
    public Integer usedTime;

    /**
     * 用户ID
     */
    public Long userId;

    /**
     * 用户名称
     */
    public String userName;

    /**
     * 支付方式 0-现金（默认），1-vip卡,2-免费赠送
     */
    public Integer payType = 0;

    /**
     * 地址
     */
    public String address;

    /**
     * 机器ID
     */
    public Long machineId = 0l;

    /**
     * 机器编码
     */
    public String machineCode;

    /**
     * 会员ID
     */
    public String memberId;

    /**
     * 会员名称
     */
    public String memberName;

    /**
     * 评价分数
     */
    public Integer commentType;

    /**
     * 评价内容
     */
    public String comment;

    /**
     * 幼苗信息
     */
    public List<SeedlingBean> medicineInfoList;

}
