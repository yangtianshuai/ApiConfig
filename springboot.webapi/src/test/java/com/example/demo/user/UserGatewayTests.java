package com.example.demo.user;


import com.example.demo.client.dto.query.UserListByParamQuery;
import com.example.demo.domain.gateway.UserGateway;
import com.example.demo.infrastructure.gateway.impl.database.model.UserDO;
import com.example.demo.infrastructure.gateway.impl.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserGatewayTests {

    @Autowired
    private UserGateway userGateway;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testfindByParam() throws Exception {
        String userName = "0745";

        var users = new ArrayList<UserDO>();
        var user = new UserDO();
        user.setUserName(userName);
        users.add(user);

        Mockito.when(this.userRepository.findAll(Example.of(user))).thenReturn(users);

        var query = UserListByParamQuery.builder().userName(userName).build();
        var _users = this.userGateway.findByParam(query);

        assertThat(_users).isNotNull();
        assertThat(_users.size()).isEqualTo(1);
        assertThat(_users.get(0).getUsername().getName()).isEqualTo(userName);
    }
}
