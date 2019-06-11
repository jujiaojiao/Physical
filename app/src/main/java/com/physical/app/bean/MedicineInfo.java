package com.physical.app.bean;


/**
 * Created by bucke on 2019/5/6.
 * 使用药物信息
 */
public class MedicineInfo {

    private Long id;

    //编码",dataType = "string")
    private String code;

    //大类型编码",dataType = "int")
    private String typeCode;

    //"名称",dataType = "string")
    private String name;

    // "数量",dataType = "int")
    private Integer num;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
