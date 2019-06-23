package cn.maidaotech.edu.sign.api.user.model;

import cn.maidaotech.edu.sign.api.commons.exception.ErrorCode;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-25 15:54
 **/
public interface UserErrors extends ErrorCode {
    public final static int ERR_USER_MOBILE_INVALID = 1000;
    public final static int ERR_USER_MOBILE_USED = 1001;
    public final static int ERR_USER_PASSWORD_INVALID = 1002;
    public final static int ERR_USER_SIGN_IN_FAIL = 1003;
    public final static int ERR_USER_USERNAME_INVALID = 1004;
    public final static int ERR_USER_USERNAME_USED = 1005;
}
