package cn.maidaotech.edu.sign.api.user.model;

import cn.maidaotech.edu.sign.api.commons.context.SessionWrapper;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-22 21:37
 **/
public class UserSessionWrapper implements SessionWrapper {
    private User user;

    private UserSession userSession;

    public UserSessionWrapper(User user, UserSession userSession) {
        this.user = user;
        this.userSession = userSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }
}
