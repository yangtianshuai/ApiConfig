package com.example.demo.application.user.query;

import com.example.demo.client.dto.data.ErrorCode;
import com.example.demo.client.dto.query.UserLoginQuery;
import com.example.demo.domain.gateway.UserGateway;
import com.example.demo.domain.user.UserEntity;
import com.example.demo.infrastructure.common.exception.ServiceBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserLoginQueryExe {

    @Autowired
    private UserGateway userGateway;

    public void execute(UserLoginQuery query) {
        var userEntity = userGateway.findPasswordInfo(query.getUsername());
        if (Objects.isNull(userEntity)) {
            throw new ServiceBizException(ErrorCode.USER_PASSWORD_ERROR);
        }

        // 校验密码是否正确
        if (!userEntity.getPassword().isCorrect(query.getPassword())) {
            throw new ServiceBizException(ErrorCode.USER_PASSWORD_ERROR);
        }
    }
}
