package com.example.demo.infrastructure.common.result;

import lombok.Data;

import java.util.Objects;

@Data
public class ResponseResult<T> {

    public ResponseResult()
    {
        this.code = 0;
        this.data = null;
        this.message = "";
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int code;
    private T data;
    public T setData(T value){
        this.data = value;
        if (value != null && this.code == 0)
        {
            this.code = 1;
        }
        return this.data;
    }

    public String message;

    public ResponseResult<Object> sucess(String message) {
        this.code = 1;
        if (!Objects.isNull(message))
        {
            this.message = message;
        }
        return new ResponseResult<>(this.code, this.message);
    }

    public ResponseResult<Object> instance(int code, String message) {
        return new ResponseResult<>(code, message);
    }

    public <T> ResponseResult<T> instance(T data) {
        if (!Objects.isNull(data))
        {
            this.code = 1;
        }
        return new ResponseResult<>(this.code, this.message, data);
    }

    public static <T> ResponseResult<T> instance(int code, String message, T data) {
        return new ResponseResult<>(code, message, data);
    }

    public Boolean isSuccess()
    {
        return this.code == 1;
    }
}