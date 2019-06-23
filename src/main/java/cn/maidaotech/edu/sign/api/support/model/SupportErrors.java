package cn.maidaotech.edu.sign.api.support.model;

import cn.maidaotech.edu.sign.api.commons.exception.ErrorCode;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-13 11:17
 **/
public interface SupportErrors extends ErrorCode {

    public static final int ERR_VCODE_INVALID = 100;
    public static final int ERR_VCODE_OVERTIME = 101;
    public static final int ERR_MOBILE_INVALID = 102;
    public static final int ERR_ALIYUN_EXCEPTION = 103;
    public static final int ERR_MOBILE_VCODE_OVERTIME = 104;
}
