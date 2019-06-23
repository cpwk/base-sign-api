package cn.maidaotech.edu.sign.api.support.service;

import org.springframework.stereotype.Service;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-13 12:12
 **/
public interface SmsService {

    String getVcode(String mobile) throws Exception;

    void sendVcode(String mobile) throws Exception;
}
