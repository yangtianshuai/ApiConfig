package com.example.demo.client.dto.query;

import com.alibaba.cola.dto.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListByParamQuery extends Query {

    private String name;

    private String userName;

}
