package org.litu.core.exception;

public class LtParamException extends RuntimeException {
	private static final long serialVersionUID = 1221L;

	public LtParamException() {
		super();
	}

	public LtParamException(String msg) {
		super(msg);
	}

	public LtParamException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
