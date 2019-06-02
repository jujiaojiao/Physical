package com.physical.app.bean;


import java.io.Serializable;

/**
 * Created by bucke on 2019/5/6.
 *个人疾病史
 */
public class MedicalHistory implements Serializable{

   public Long id;

   public String code;

   public String name;

   public boolean select = false ;
}
