package cn.maidaotech.edu.sign.api.user.repository;

import cn.maidaotech.edu.sign.api.user.model.UserSession;
import cn.maidaotech.edu.sign.api.commons.repository.BaseRepository;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-06-10 16:14
 **/
public interface UserSessionRepository extends BaseRepository<UserSession, Integer> {

    UserSession findByToken(String token);
}






