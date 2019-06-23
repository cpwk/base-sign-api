package cn.maidaotech.edu.sign.api.commons.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-19 12:11
 **/
public class JsonView extends AbstractView {

    private Object result;

    public JsonView(Object result) {
        super();
        this.result = result;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest,
                                           HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
