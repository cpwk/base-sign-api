package cn.maidaotech.edu.sign.api.commons.exception;

public class RuntimeServiceException extends ServiceException {
    private static final long serialVersionUID = 550893169395750576L;

    public RuntimeServiceException() {
        super(ErrorCode.ERR_UNKNOWN_ERROR);
    }

    public RuntimeServiceException(Throwable t) {
        this();
        this.initCause(t);
    }

    public RuntimeServiceException(String s) {
        this();
        this.initCause(new RuntimeException(s));
    }

}
