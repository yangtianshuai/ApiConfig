package com.example.demo.client.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;

@Data
public class UserRegisterCmd extends Command {

    public UserRegisterCmd(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * OA系统ID
     */
    private String employeeId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名（较常用，故放在用户主表）
     */
    private String name;

    /**
     * 科室代码
     */
    private String deptCode;

}
