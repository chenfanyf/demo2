package com.chenfanyf.demo2.entity;


import com.chenfanyf.demo2.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true) //有继承的父类才可以用的注解
@Data
@Entity
@Table(name = "cf_user")
public class User extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //设置id自增长
    private Long id;


    private String username;

    @JsonIgnore //不传递该值给前台的属性或接口
    private String password;

    @Enumerated(EnumType.STRING) //采用枚举类型与数据库进行交互
    private RoleEnum role;


}
