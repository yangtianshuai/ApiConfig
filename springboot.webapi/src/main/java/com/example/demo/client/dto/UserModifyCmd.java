package com.example.demo.client.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;

@Data
public class UserModifyCmd extends Command {

    public UserModifyCmd(String id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 科室编号
     */
    private String deptCode;

    /**
     * 姓名（较常用，故放在用户主表）
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
     * 手机号
     */
    private String phoneNo;

}