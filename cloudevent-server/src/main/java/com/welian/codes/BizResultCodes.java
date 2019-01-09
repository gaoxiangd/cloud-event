package com.welian.codes;

import org.sean.framework.exception.CodeException;
import com.welian.utils.I18N;

import static com.welian.codes.BizResultCodes.RESOURCE.ERROR_RESOURCE_UNKNOW;

/**
 * Description: 错误码
 * Date: 2016/10/31 18:12
 *
 * @author Sean.xie
 */
public final class BizResultCodes {
    public static enum Code implements CodeException.ExceptionInfo {
        RESOURCE_UNKNOW(ERROR_RESOURCE_UNKNOW, "error_resource_unknow");

        private int code;
        private String msg;

        Code(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return I18N.getErrorMsg(msg);
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return String.format("code:%d,msg:%s", code, getMsg());
        }
    }

    public static final class RESOURCE {
        public static final int ERROR_RESOURCE_UNKNOW = 21000;
    }

}
