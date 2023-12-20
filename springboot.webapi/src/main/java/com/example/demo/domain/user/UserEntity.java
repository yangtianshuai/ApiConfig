package com.example.demo.domain.user;

import com.alibaba.cola.domain.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class UserEntity {

    private String id;

    private Employee employee;

    /**
     * 用户名
     */
    private UserName username;
    /**
     * 密码
     */
    private UserPassword password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 工作类别
     */
    private String job;

    /**
     * 职称
     */
    private String title;
    /**
     * 科室代码
     */
    private String deptCode;
    /**
     * 手机号
     */
    private String phoneNo;

}
