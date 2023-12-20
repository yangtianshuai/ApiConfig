package com.example.demo.remote;

import com.example.demo.domain.user.Employee;
import com.example.demo.infrastructure.common.result.ResponseResult;
import com.example.demo.infrastructure.remote.EmployeeClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeClientTests {
    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeClient employeeClient;

    @Test
    @DisplayName("获取员工接口")
    public void testImportEmployee(){

        int employeeId = 106593;
        String url = "http://www.baidu.com?id="+employeeId;
        var pr = new ParameterizedTypeReference<ResponseResult<Employee>>() {};
        var exchange = this.restTemplate.exchange(url, HttpMethod.GET, null, pr);

        var result = exchange.getBody().getData();

        assertThat(result).isNotNull();
        assertThat(result.getEmployeeId()).isEqualTo(employeeId);
    }

}
