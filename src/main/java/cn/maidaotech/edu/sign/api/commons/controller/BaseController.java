package cn.maidaotech.edu.sign.api.commons.controller;

import cn.maidaotech.edu.sign.api.commons.exception.ArgumentServiceException;
import cn.maidaotech.edu.sign.api.commons.util.WebUtils;
import com.sunnysuperman.commons.bean.Bean;
import com.sunnysuperman.commons.bean.ParseBeanOptions;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-16 18:23
 **/
public class BaseController {

    protected ModelAndView feedback() {
        return feedback(null);
    }

    protected ModelAndView feedback(Object obj) {
        Object result = obj != null ? obj : "success";
        Map<String, Object> data = new HashMap<>();
        data.put("errcode", 0);
        data.put("result", result);
        return new ModelAndView(new JsonView(data));
    }

    protected static <T> T parseModel(String modelJSON, T model) throws ServiceException {
        return parseModel(modelJSON, model, null, null);
    }

    protected static <T> T parseModel(String modelJSON, T model, String key) throws ServiceException {
        return parseModel(modelJSON, model, key, null);
    }

    protected static <T> T parseModel(String modelJSON, T model, String key, ParseBeanOptions options)
            throws ServiceException {
        try {
            return Bean.fromJson(modelJSON, model, options);
        } catch (Exception e) {
            throw new ArgumentServiceException((key != null ? key : "model"));
        }
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ra.getRequest();
    }

    protected String getRemoteAddress() {
        return WebUtils.getRemoteAddress(getRequest());
    }
}
