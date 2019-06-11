package com.physical.app.bean;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/26
 * 描述：
 */
public class RecommendBean {

    /**
     * id : 2
     * lastVer : 0
     * isValid : 1
     * createTime : 1559362487000
     * opTime : 1559362487000
     * machineCode : null
     * createPerson : 管理员
     * updatePerson : 管理员
     * name : 测试
     * hisId : 10
     * hisName : 高血压
     * medicineInfoList : [{"id":200,"code":"110","typeCode":"seedling","name":"豌豆苗","num":3},{"id":201,"code":"120","typeCode":"seedling","name":"黄瓜苗","num":4},{"id":202,"code":"130","typeCode":"seedling","name":"牛蒡苗","num":6},{"id":309,"code":"290","typeCode":"seedling","name":"燕麦苗","num":6}]
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
    public int hisId;
    public String hisName;
    public List<MedicineInfoListBean> medicineInfoList;

    public static class MedicineInfoListBean {
        /**
         * id : 200
         * code : 110
         * typeCode : seedling
         * name : 豌豆苗
         * num : 3
         */

        public int id;
        public String code;
        public String typeCode;
        public String name;
        public int num;
    }
}
