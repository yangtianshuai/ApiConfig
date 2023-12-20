package com.example.demo.user;

import com.example.demo.infrastructure.gateway.impl.database.model.UserDO;
import com.example.demo.infrastructure.gateway.impl.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void testfindByUserName() throws Exception {
        String userName = "0745";
        var user = new UserDO();
        user.setId("123");
        user.setName("杨天帅");
        user.setUserName(userName);
        user.setEmployeeId(100);

        //this.entityManager.persist(user);
        var users = this.userRepository.findByUserName(userName);

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getUserName()).isEqualTo(userName);
    }
}
