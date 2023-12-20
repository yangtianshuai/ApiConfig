package com.example.demo.client.dto.data;

public enum ErrorCode {

    USER_USERNAME_REPEAT("USER_001", "用户名重复"),
    USER_UNDEFINED("USER_002", "用户不存在"),
    USER_PASSWORD_ERROR("USER_003", "用户名或密码不正确");

    private final String errCode;
    private final String errDesc;

    ErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }
}
