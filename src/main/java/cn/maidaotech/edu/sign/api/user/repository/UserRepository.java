package cn.maidaotech.edu.sign.api.user.repository;

import cn.maidaotech.edu.sign.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-19 10:19
 **/

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByMobile(String mobile);

}
