package cn.maidaotech.edu.sign.api.commons.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-22 15:45
 **/
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

}
