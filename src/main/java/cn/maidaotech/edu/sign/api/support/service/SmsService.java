package cn.maidaotech.edu.sign.api.support.service;

import cn.maidaotech.edu.sign.api.support.model.VCode;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-13 12:12
 **/
public interface SmsService {

    VCode getVcode(Long key) throws Exception;

    void sendVcode(Long key, String mobile) throws Exception;
}
