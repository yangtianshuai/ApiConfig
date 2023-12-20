package com.example.demo.application.event.handler;

import com.example.demo.domain.event.UserRegisterEvent;
import com.example.demo.domain.user.UserEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public class UserRegisterHandler {
    @Async
    @EventListener(value = UserRegisterEvent.class)
    public void registryEventSendMailHandler(UserRegisterEvent userRegisterEvent) {
        var user = (UserEntity) userRegisterEvent.getSource();
        String phoneNo = user.getPhoneNo();
        //向短信平台发送短信
    }

    @RabbitListener
    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
