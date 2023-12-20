package com.example.demo.client.dto.query;

import com.alibaba.cola.dto.Query;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginQuery extends Query {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
