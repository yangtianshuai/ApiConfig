package com.example.demo.infrastructure.convertor;

import com.example.demo.domain.user.UserEntity;
import com.example.demo.domain.user.UserName;
import com.example.demo.infrastructure.gateway.impl.database.model.UserDO;
import com.example.demo.infrastructure.remote.EmployeeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserConvertor {

    @Autowired
    private static EmployeeClient employeeClient;

    public static UserEntity toEntity(UserDO userDO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDO.getId());
        userEntity.setUsername(new UserName(userDO.getUserName()));
        userEntity.setName(userDO.getName());
        return userEntity;
    }

    public static UserDO toAddUserDO(UserEntity userEntity) {
        var userDO = new UserDO();
        userDO.setId(userEntity.getId());
        String userName = userEntity.getUsername().getName();
        userDO.setEmpNo(userName);
        userDO.setUserName(userName);
        userDO.setPassword(userEntity.getPassword().getEncryptPassword());
        userDO.setName(userEntity.getName());
        int employeeId = Integer.parseInt(userEntity.getEmployee().getEmployeeId());
        //拉取手机号
        var employee = employeeClient.importEmployee(employeeId);
        userDO.setEmployeeId(employeeId);
        userDO.setPhone(employee.getPhone());
        userDO.setCreateTime(LocalDateTime.now());

        return userDO;
    }

    public static void toModifyUserDO(UserEntity userEntity, UserDO userDO) {
        userDO.setName(userEntity.getName());
        userDO.setUserName(userEntity.getUsername().getName());
        userDO.setJob(userEntity.getJob());
    }
}
