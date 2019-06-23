package cn.maidaotech.edu.sign.api.commons.context;

import cn.maidaotech.edu.sign.api.user.model.UserSessionWrapper;
import cn.maidaotech.edu.sign.api.commons.exception.ArgumentServiceException;
import cn.maidaotech.edu.sign.api.commons.exception.ErrorCode;
import cn.maidaotech.edu.sign.api.commons.exception.ServiceException;
import cn.maidaotech.edu.sign.api.commons.resources.LocaleBundles;

public class Contexts {

    public static void set(Context context) {
        SessionThreadLocal.getInstance().set(context);
    }

    public static Context get() {
        return SessionThreadLocal.getInstance().get();
    }

    public static SessionWrapper getSession() {
        return get().getSession();
    }

    public static String ensureLocale() {
        Context context = get();
        if (context == null) {
            return LocaleBundles.getDefaultLocale();
        }
        return context.getLocale();
    }

    public static Integer requestUserId() throws ServiceException {
        Context context = get();
        if (context == null) {
            throw new ServiceException(ErrorCode.ERR_SESSION_EXPIRES);
        }
        Integer id = sessionUserId();
        if (id == null) {
            throw new ArgumentServiceException("need userId");
        }
        return id;
    }

    public static Integer sessionUserId() throws ServiceException {
        Context context = get();
        if (context == null) {
            return null;
        }
        SessionWrapper wrap = context.getSession();
        Integer id = null;

        if (wrap instanceof UserSessionWrapper) {
            id = ((UserSessionWrapper) wrap).getUser().getId();
        }
        return id;
    }

}
