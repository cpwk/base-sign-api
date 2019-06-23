package cn.maidaotech.edu.sign.api.user.service;

import cn.maidaotech.edu.sign.api.user.model.User;
import cn.maidaotech.edu.sign.api.user.model.UserSession;

import java.util.Map;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-19 11:12
 **/
public interface UserService {

    void signUp(User user, Long key) throws Exception;

    Map<String, Object> signIn(String username, String password, String ip) throws Exception;

    UserSession getUserSession(String token) throws Exception;

    User getById(Integer id) throws Exception;

    User findById(Integer id) throws Exception;
}
