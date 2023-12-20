package com.example.demo.infrastructure.common.exception;

import com.alibaba.cola.exception.BizException;
import com.example.demo.client.dto.data.ErrorCode;

public class ServiceBizException extends BizException {
    public ServiceBizException(ErrorCode errorCode) {

        super(errorCode.getErrCode(), errorCode.getErrDesc());
    }
}
