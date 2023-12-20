package com.example.demo.infrastructure.gateway.impl.database.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="STAFF_DICT")
public class UserDO implements Serializable {

    /**
     * 流水号
     */
    @Id
    @Column(name = "ID")
    private String id;
    /**
     * 工号
     */
    @Column(name = "EMP_NO")
    private String empNo;
    /**
     * 科室编号
     */
    @Column(name = "DEPT_CODE")
    private String deptCode;
    /**
     * 姓名
     */
    @Column(name = "NAME")
    private String name;
    /**
     * 职位
     */
    @Column(name = "JOB")
    private String job;
    /**
     * 职务
     */
    @Column(name = "TITLE")
    private String title;
    /**
     * 用户名
     */
    @Column(name = "USER_NAME")
    private String userName;
    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;
    /**
     * 当前密码
     */
    @Column(name = "PASSWORD2")
    private String currentPassword;
    /**
     * OA系统ID
     */
    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;
    /**
     * 手机号
     */
    @Column(name = "PHONE_NUMBER")
    private String phone;
    /**
     * 状态 0-启用；1-停用
     */
    @Column(name = "STATUS")
    private String status;
    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @Column(name = "ALTER_DATE")
    private LocalDateTime updateTime;
}
