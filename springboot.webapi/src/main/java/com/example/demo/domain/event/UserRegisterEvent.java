package com.example.demo.domain.event;

import org.springframework.context.ApplicationEvent;

public class UserRegisterEvent<UserEntity> extends ApplicationEvent {

    public UserRegisterEvent(UserEntity user) {
        super(user);
    }
}
