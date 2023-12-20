package com.example.demo.application.assembler;

import com.example.demo.client.dto.UserModifyCmd;
import com.example.demo.client.dto.UserRegisterCmd;
import com.example.demo.client.dto.data.UserVO;
import com.example.demo.domain.user.Employee;
import com.example.demo.domain.user.UserEntity;
import com.example.demo.domain.user.UserName;
import com.example.demo.domain.user.UserPassword;

public class UserAssembler {

    public static UserEntity toEntity(UserRegisterCmd co) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(new UserName(co.getUsername()));
        userEntity.setPassword(new UserPassword(co.getPassword()));
        userEntity.setName(co.getName());
        userEntity.setEmployee(new Employee(co.getEmployeeId()));
        userEntity.setDeptCode(co.getDeptCode());
        return userEntity;
    }

    public static UserEntity toEntity(UserModifyCmd co) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(co.getId());
        userEntity.setUsername(new UserName(co.getUsername()));
        userEntity.setName(co.getName());
        userEntity.setDeptCode(co.getDeptCode());
        userEntity.setPhoneNo(co.getPhoneNo());
        return userEntity;
    }

    public static UserVO toValueObject(UserEntity userEntity) {
        UserVO userVO = new UserVO();
        userVO.setId(userEntity.getId());
        userVO.setName(userEntity.getName());
        userVO.setUsername(userEntity.getUsername().getName());
        userEntity.setDeptCode(userEntity.getDeptCode());
        userVO.setPhoneNo(userEntity.getPhoneNo());

        return userVO;
    }

}
