package com.example.demo.application.user.query;

import com.alibaba.cola.dto.SingleResponse;
import com.example.demo.application.assembler.UserAssembler;
import com.example.demo.client.dto.data.ErrorCode;
import com.example.demo.client.dto.data.UserVO;
import com.example.demo.domain.gateway.UserGateway;
import com.example.demo.domain.user.UserEntity;
import com.example.demo.infrastructure.common.exception.ServiceBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserInfoQueryExe {

    @Autowired
    private UserGateway userGateway;

    public SingleResponse<UserVO> execute(String id) {
        var userEntity = userGateway.findById(id);
        if (Objects.isNull(userEntity)) {
            throw new ServiceBizException(ErrorCode.USER_UNDEFINED);
        }

        return SingleResponse.of(UserAssembler.toValueObject(userEntity));
    }
}
