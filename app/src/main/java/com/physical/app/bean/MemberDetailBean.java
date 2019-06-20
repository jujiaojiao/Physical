package com.physical.app.bean;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/6/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class MemberDetailBean {
    public String id;

    /**
     * 名称
     */
    public String name;

    /**
     * 昵称
     */
    public String nickName;

    /**
     * 手机号
     */
    public String mobile;

    /**
     * 身份证
     */
    public String idCard;

    /**
     * 性别
     */
    public String sex;

    /**
     * 生日
     */
    public long birthday;

    /**
     * 升高
     */
    public String height;

    /**
     * 体重
     */
    public String weight;

    /**
     * 首次诊疗时间
     */
    public long firstTime;

    /**
     * 是否是vip客户
     */
    public boolean vip = false;

    /**
     * vip剩余疗养次数
     */
    public String vipTimes;

    /**
     * 疗养次数
     */
    public String usedTime;

    /**
     * 所属管理员ID
     */
    public String userId;

    /**
     * 金额
     */
    public String totalMoney;

    /**
     * 病史
     *
     * @return
     */
    public List<MedicalHistory> medicalHistoryList;

    /**
     * 诊疗纪录
     */
    public List<MemberCardVo> memberCaseVoList;
}
