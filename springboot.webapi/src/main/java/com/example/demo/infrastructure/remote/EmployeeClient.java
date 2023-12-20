package com.example.demo.infrastructure.remote;

import com.example.demo.domain.user.Employee;
import com.example.demo.infrastructure.common.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@Component
public class EmployeeClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.employee}")
    private String employeeUrl;

    public Employee importEmployee(int imployeeId){
        var pr = new ParameterizedTypeReference<ResponseResult<Employee>>() {};
        var exchange = restTemplate.exchange(employeeUrl, HttpMethod.GET, null, pr);

        return exchange.getBody().getData();
    }
}
