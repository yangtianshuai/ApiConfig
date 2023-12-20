package com.example.demo.application.user.query;

import com.alibaba.cola.dto.MultiResponse;
import com.example.demo.application.assembler.UserAssembler;
import com.example.demo.client.dto.data.UserVO;
import com.example.demo.client.dto.query.UserListByParamQuery;
import com.example.demo.domain.gateway.UserGateway;
import com.example.demo.domain.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserListByParamQueryExe {

    @Autowired
    private UserGateway userGateway;

    public MultiResponse<UserVO> execute(UserListByParamQuery query) {
        var userEntities = userGateway.findByParam(query);
        var userVOList = userEntities.stream()
                .map(UserAssembler::toValueObject)
                .collect(Collectors.toList());

        return MultiResponse.of(userVOList);
    }
}
