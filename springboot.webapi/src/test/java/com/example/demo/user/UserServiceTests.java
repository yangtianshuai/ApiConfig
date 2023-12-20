package com.example.demo.user;

import com.example.demo.application.user.query.UserListByParamQueryExe;
import com.example.demo.client.dto.query.UserListByParamQuery;
import com.example.demo.domain.gateway.UserGateway;
import com.example.demo.domain.user.UserEntity;
import com.example.demo.domain.user.UserName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserListByParamQueryExe userListByParamQueryExe;
    @MockBean
    private UserGateway userGateway;

    @Test
    public void testGetUserByName() throws Exception {
        String userName = "0745";

        var userVOList = new ArrayList<UserEntity>();
        UserEntity userEntity = new UserEntity();
        userEntity.setId("123");
        userEntity.setName("杨天帅");
        userEntity.setUsername(new UserName(userName));
        userVOList.add(userEntity);

        var query = UserListByParamQuery.builder().name(userName).build();
        Mockito.when(this.userGateway.findByParam(query)).thenReturn(userVOList);

        var users = this.userListByParamQueryExe.execute(query);

        assertThat(users).isNotNull();
        assertThat(users.getData().size()).isEqualTo(1);
        assertThat(users.getData().get(0).getUsername()).isEqualTo(userName);
    }
}
