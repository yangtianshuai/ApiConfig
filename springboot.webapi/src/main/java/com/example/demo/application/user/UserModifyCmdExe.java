package com.example.demo.application.user;

import com.example.demo.application.assembler.UserAssembler;
import com.example.demo.client.dto.UserModifyCmd;
import com.example.demo.client.dto.data.ErrorCode;
import com.example.demo.client.dto.data.UserVO;
import com.example.demo.domain.gateway.UserGateway;
import com.example.demo.domain.user.UserEntity;
import com.example.demo.infrastructure.common.exception.ServiceBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserModifyCmdExe {

    @Autowired
    private UserGateway userGateway;

    public UserVO execute(UserModifyCmd cmd) {
        // check 用户名是否重复
        if (userGateway.checkByUsername(cmd.getId(), cmd.getUsername())) {
            throw new ServiceBizException(ErrorCode.USER_USERNAME_REPEAT);
        }

        var userEntity = userGateway.save(UserAssembler.toEntity(cmd));
        return UserAssembler.toValueObject(userEntity);
    }

}
