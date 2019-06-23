package cn.maidaotech.edu.sign.api.commons.controller;

import cn.maidaotech.edu.sign.api.user.model.User;
import cn.maidaotech.edu.sign.api.user.model.UserSession;
import cn.maidaotech.edu.sign.api.user.model.UserSessionWrapper;
import cn.maidaotech.edu.sign.api.user.service.UserService;
import cn.maidaotech.edu.sign.api.commons.context.Context;
import cn.maidaotech.edu.sign.api.commons.context.Contexts;
import cn.maidaotech.edu.sign.api.commons.context.SessionThreadLocal;
import cn.maidaotech.edu.sign.api.commons.exception.ErrorCode;
import cn.maidaotech.edu.sign.api.commons.exception.RuntimeServiceException;
import cn.maidaotech.edu.sign.api.commons.exception.ServiceException;
import cn.maidaotech.edu.sign.api.commons.util.StringUtils;
import cn.maidaotech.edu.sign.api.commons.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DefaultInterceptor implements HandlerInterceptor, WebApiConstant {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        if (CrossDomainHandler.handle(request, response)) {
            return false;
        }

        Context context = new Context();
        context.setRequestIp(WebUtils.getRemoteAddress(request));
        SessionThreadLocal.getInstance().set(context);

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        boolean authorized = false;
        Action action = handlerMethod.getMethodAnnotation(Action.class);
        if (action == null) {
            throw new RuntimeServiceException(handlerMethod.getMethod().getName() + "needs annotation Action");
        }
        for (ActionSession session : action.session()) {
            if (session == ActionSession.USER) {
                authorized = findUserSession(request) != null ? true : false;
            } else if (session == ActionSession.ADMIN) {
                authorized = true;
            } else if (session == ActionSession.NONE) {
                authorized = true;
            } else {
                authorized = false;
            }
        }
        if (!authorized) {
            throw new ServiceException(ErrorCode.ERR_SESSION_EXPIRES);
        }
        return true;
    }

    private UserSessionWrapper findUserSession(HttpServletRequest request) throws Exception {
        String token = WebUtils.getHeader(request, KEY_USER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        UserSession session = userService.getUserSession(token);
        if (session == null) {
            return null;
        }
        User user = userService.getById(session.getUserId());
        if (user == null) {
            return null;
        }
        UserSessionWrapper wrapper = new UserSessionWrapper(user, session);
        Context context = Contexts.get();
        context.setSession(wrapper);
        return wrapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) {
    }

}