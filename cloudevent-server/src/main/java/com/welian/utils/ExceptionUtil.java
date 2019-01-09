package com.welian.utils;

import org.sean.framework.code.ResultCodes;
import org.sean.framework.exception.CodeException;
import org.sean.framework.exception.CodeExceptionUtil;

/**
 * Created by memorytale on 2017/4/21.
 */
public class ExceptionUtil {

    public static CodeException createException(CodeException.ExceptionInfo info, String msg) {
        CodeException exception = new CodeException(new ResultCodes.DataCode(info, msg));
        return exception;
    }

    public static CodeException createParamException(String msg) {
        return CodeExceptionUtil.newParamException(msg);
    }

    public static CodeException createNoPriviligeException(String msg) {

        return new CodeException(ResultCodes.Code.COMMON_ERROR_AUTHORITY_FAILED,msg);

    }
    public static CodeException createWarnException(String msg){
        CodeException exception = new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, msg);
        exception.setLevel(CodeException.Level.WARNING);
        return exception;
    }

}
