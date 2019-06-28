package cn.maidaotech.edu.sign.api.user.service;

import cn.maidaotech.edu.sign.api.commons.cache.CacheOptions;
import cn.maidaotech.edu.sign.api.commons.cache.KvCacheFactory;
import cn.maidaotech.edu.sign.api.commons.cache.KvCacheWrap;
import cn.maidaotech.edu.sign.api.commons.exception.ServiceException;
import cn.maidaotech.edu.sign.api.commons.util.CollectionUtil;
import cn.maidaotech.edu.sign.api.commons.util.DateUtils;
import cn.maidaotech.edu.sign.api.commons.util.StringUtils;
import cn.maidaotech.edu.sign.api.support.model.SupportErrors;
import cn.maidaotech.edu.sign.api.support.model.VCode;
import cn.maidaotech.edu.sign.api.support.service.SmsService;
import cn.maidaotech.edu.sign.api.support.service.VCodeService;
import cn.maidaotech.edu.sign.api.user.model.User;
import cn.maidaotech.edu.sign.api.user.model.UserConfig;
import cn.maidaotech.edu.sign.api.user.model.UserErrors;
import cn.maidaotech.edu.sign.api.user.model.UserSession;
import cn.maidaotech.edu.sign.api.user.repository.UserRepository;
import cn.maidaotech.edu.sign.api.user.repository.UserSessionRepository;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-19 11:14
 **/
@Service
public class UserServiceImp implements UserService, UserErrors {

    @Autowired
    private UserConfig userConfig;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private VCodeService vCodeService;
    @Autowired
    private SmsService smsService;

    @Autowired
    private KvCacheFactory kvCacheFactory;
    private KvCacheWrap<String, UserSession> sessionCache;
    private KvCacheWrap<Integer, User> userCache;

    @PostConstruct
    public void init() {
        sessionCache = kvCacheFactory.create(new CacheOptions("usr_sess", 1, 1800),
                new RepositoryProvider<String, UserSession>() {

                    @Override
                    public UserSession findByKey(String token) throws Exception {
                        return userSessionRepository.findByToken(token);
                    }

                    @Override
                    public Map<String, UserSession> findByKeys(Collection<String> collection) throws Exception {
                        throw new UnsupportedOperationException("findByKeys");
                    }
                }, new BeanModelConverter<>(UserSession.class));

        userCache = kvCacheFactory.create(new CacheOptions("usr", 1, 1800),
                new RepositoryProvider<Integer, User>() {

                    @Override
                    public User findByKey(Integer id) throws Exception {
                        return userRepository.getOne(id);
                    }

                    @Override
                    public Map<Integer, User> findByKeys(Collection<Integer> ids) throws Exception {
                        throw new UnsupportedOperationException("findByKeys");
                    }
                }, new BeanModelConverter<>(User.class));
    }

    @Override
    public void signUp(User user, VCode picCode, VCode smsCode) throws Exception {
        if (StringUtils.isNotEqual(picCode.getCode(), vCodeService.getVCode(picCode.getKey()).getCode())) {
            throw new ServiceException(SupportErrors.ERR_VCODE_INVALID);
        }
        if (!StringUtils.isChinaMobile(user.getMobile())) {
            throw new ServiceException(ERR_USER_MOBILE_INVALID);
        }
        if (StringUtils.isNotEqual(smsCode.getCode(), smsService.getVcode(smsCode.getKey()).getCode())) {
            throw new ServiceException(SupportErrors.ERR_MOBILE_INVALID);
        }
        if (!StringUtils.validatePassword(user.getPassword())) {
            throw new ServiceException(ERR_USER_PASSWORD_INVALID);
        }
        User exist = userRepository.findByMobile(user.getMobile());
        if (exist != null) {
            throw new ServiceException(ERR_USER_MOBILE_USED);
        }
        String encrypt = StringUtils.encryptPassword(user.getPassword(), userConfig.getPasswordSalt());
        user.setPassword(encrypt);
        userRepository.save(user);
    }

    @Override
    public Map<String, Object> signIn(String username, String password, String ip) throws Exception {
        User user = null;
        if (StringUtils.isChinaMobile(username)) {
            user = userRepository.findByMobile(username);
        }
        String encrypt = StringUtils.encryptPassword(password, userConfig.getPasswordSalt());
        if (user == null || !user.getPassword().equals(encrypt)) {
            throw new ServiceException(ERR_USER_SIGN_IN_FAIL);
        }
        UserSession userSession = createUserSession(user, ip);
        return CollectionUtil.arrayAsMap("user", user, "session", userSession);
    }

    private UserSession createUserSession(User user, String ip) {
        UserSession session = new UserSession();
        session.setUserId(user.getId());
        session.setToken(StringUtils.getToken());
        session.setSigninAt(System.currentTimeMillis());
        session.setExpireAt(DateUtils.getDate_days(new Date(), userConfig.getSessionDays()).getTime());
        session.setIp(ip);
        userSessionRepository.save(session);
        return session;
    }

    @Override
    public User findById(Integer id) throws Exception {
        return userCache.findByKey(id);
    }

    @Override
    public User getById(Integer id) throws Exception {
        User user = findById(id);
        if (user == null) {
            throw new ServiceException(ERR_DATA_NOT_FOUND);
        }
        return user;
    }

    @Override
    public UserSession getUserSession(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        UserSession session = sessionCache.findByKey(token);
        if (session.getExpireAt() < System.currentTimeMillis()) {
            throw new ServiceException(ERR_SESSION_EXPIRES);
        }
        return session;
    }
}
