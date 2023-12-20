package com.example.demo.domain.user;

import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;

public class UserName {

    private final String name;

    public UserName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new ValidationException("用户名不能为空");
        }
        if(name.length() != 4) {
            throw new ValidationException("用户名长度必须是4位");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
