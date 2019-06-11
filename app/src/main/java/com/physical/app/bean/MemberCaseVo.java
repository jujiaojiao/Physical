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
    private Integer lampLight;

    /**
     * 苗光
     */
    private Integer seedlingLight;

    /**
     * 超声波
     */
    private Integer superaudible;

    /**
     * 短超波
     */
    private Integer ultrashortWave;

    /**
     * 磁场强度
     */
    private Integer  magneticIntensity;

    /**
     * 总疗养时间
     */
    private Integer totalTime;

    /**
     * 0-疗养中,1-暂停,2-完成
     */
    private int status;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 上一次更新时间
     */
    private Date lastUpdateTime;

    /**
     * 已疗养时间
     */
    private Integer usedTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 支付方式 0-现金（默认），1-vip卡,2-免费赠送
     */
    private Integer payType = 0;

    /**
     * 地址
     */
    private String address;

    /**
     * 机器ID
     */
    private Long machineId = 0l;

    /**
     * 机器编码
     */
    private String machineCode;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 评价分数
     */
    private Integer commentType;

    /**
     * 评价内容
     */
    private String comment;

    /**
     * 幼苗信息
     */
    private List<MedicineInfo> medicineInfoList;

    public Integer getLampLight() {
        return lampLight;
    }

    public void setLampLight(Integer lampLight) {
        this.lampLight = lampLight;
    }

    public Integer getSeedlingLight() {
        return seedlingLight;
    }

    public void setSeedlingLight(Integer seedlingLight) {
        this.seedlingLight = seedlingLight;
    }

    public Integer getSuperaudible() {
        return superaudible;
    }

    public void setSuperaudible(Integer superaudible) {
        this.superaudible = superaudible;
    }

    public Integer getUltrashortWave() {
        return ultrashortWave;
    }

    public void setUltrashortWave(Integer ultrashortWave) {
        this.ultrashortWave = ultrashortWave;
    }

    public Integer getMagneticIntensity() {
        return magneticIntensity;
    }

    public void setMagneticIntensity(Integer magneticIntensity) {
        this.magneticIntensity = magneticIntensity;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public List<MedicineInfo> getMedicineInfoList() {
        return medicineInfoList;
    }

    public void setMedicineInfoList(List<MedicineInfo> medicineInfoList) {
        this.medicineInfoList = medicineInfoList;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
