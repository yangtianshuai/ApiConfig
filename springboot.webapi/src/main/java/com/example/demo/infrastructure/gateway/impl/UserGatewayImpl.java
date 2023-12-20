package com.example.demo.infrastructure.gateway.impl;


import com.example.demo.client.dto.data.ErrorCode;
import com.example.demo.client.dto.query.UserListByParamQuery;
import com.example.demo.domain.event.UserRegisterEvent;
import com.example.demo.domain.gateway.UserGateway;
import com.example.demo.domain.user.UserEntity;
import com.example.demo.domain.user.UserPassword;
import com.example.demo.infrastructure.common.event.DomainEventPublisher;
import com.example.demo.infrastructure.common.exception.ServiceBizException;
import com.example.demo.infrastructure.convertor.UserConvertor;
import com.example.demo.infrastructure.gateway.impl.database.model.UserDO;
import com.example.demo.infrastructure.gateway.impl.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserGatewayImpl implements UserGateway {

    @Autowired
    private DomainEventPublisher domainEventPublisher;
    private final UserRepository userRepository;

    @Autowired
    public UserGatewayImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserEntity save(UserEntity userEntity) {
        // 新增
        if (Objects.isNull(userEntity.getId())) {
            return addUser(userEntity);
        }

        // 修改
        return modifyUser(userEntity);
    }

    @Override
    public UserEntity findById(String id) {
        Optional<UserDO> findById = userRepository.findById(id);
        if (findById.isEmpty()) {
            throw new ServiceBizException(ErrorCode.USER_UNDEFINED);
        }
        UserDO userDO = findById.get();

        return UserConvertor.toEntity(userDO);
    }

    @Override
    public List<UserEntity> findByParam(UserListByParamQuery query) {
        List<UserEntity> entities = new ArrayList<>();

        UserDO user = new UserDO();
        user.setName(query.getName());
        user.setUserName(query.getUserName());

        userRepository.findAll(Example.of(user)).forEach(userDO -> {
            entities.add(UserConvertor.toEntity(userDO));
        });
        return entities;
    }

    @Override
    public UserEntity findPasswordInfo(String username) {

        var users = userRepository.findByUserName(username);
        if (users.size()==0) {
            return null;
        }

        var password = users.get(0).getPassword();
        if (Objects.isNull(password)) {
            return null;
        }

        var userEntity = new UserEntity();
        userEntity.setPassword(new UserPassword(new UserPassword.EncryptPassword(password)));
        return userEntity;
    }

    @Override
    public Boolean checkByUsername(String userId, String username) {
        var userDO = new UserDO();
        userDO.setId(userId);
        userDO.setUserName(username);
        var user = Example.of(userDO);

        return userRepository.exists(user);
    }

    /**
     * 新增用户
     */
    private UserEntity addUser(UserEntity userEntity) {
        // 初始化用户信息
        UserDO userDO = UserConvertor.toAddUserDO(userEntity);

        userRepository.save(userDO);
        //发布领域事件，后面需要考虑事务问题
        domainEventPublisher.publish(new UserRegisterEvent(userEntity));
        return UserConvertor.toEntity(userDO);
    }
    /**
     * 修改用户
     */
    private UserEntity modifyUser(UserEntity userEntity) {
        Optional<UserDO> findById = userRepository.findById(userEntity.getId());
        if (findById.isEmpty()) {
            throw new ServiceBizException(ErrorCode.USER_UNDEFINED);
        }

        UserDO userDO = findById.get();
        // 更新用户信息
        UserConvertor.toModifyUserDO(userEntity, userDO);

        userDO.setUpdateTime(LocalDateTime.now());
        userRepository.save(userDO);

        return UserConvertor.toEntity(userDO);
    }

}
