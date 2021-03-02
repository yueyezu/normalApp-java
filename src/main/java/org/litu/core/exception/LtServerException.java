package org.litu.core.exception;

import org.litu.core.enums.ResultEnum;

public class LtServerException extends RuntimeException {
    private static final long serialVersionUID = 1221L;

    /**
     * 附带错误信息的枚举
     */
    private ResultEnum errorMsg = null;

    public LtServerException() {
        super();
        this.errorMsg = ResultEnum.ServerError;
    }

    /**
     * @param error 错误信息枚举
     */
    public LtServerException(ResultEnum error) {
        super(error.getText());
        this.errorMsg = error;
    }

    /**
     * @param msg 信息
     */
    public LtServerException(String msg) {
        super(msg);
    }

    /**
     * @param msg   信息
     * @param error 错误枚举
     */
    public LtServerException(String msg, ResultEnum error) {
        super(msg);
        this.errorMsg = error;
    }

    /**
     * @param message 错误信息
     * @param cause   错误原因
     */
    public LtServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param msg   信息
     * @param error 错误枚举
     * @param cause 错误原因
     */
    public LtServerException(String msg, ResultEnum error, Throwable cause) {
        super(msg, cause);
        this.errorMsg = error;
    }

    public ResultEnum getErrorMsg() {
        return errorMsg;
    }
}
