package cn.maidaotech.edu.sign.api.support.controller;

import cn.maidaotech.edu.sign.api.commons.controller.Action;
import cn.maidaotech.edu.sign.api.commons.controller.ActionSession;
import cn.maidaotech.edu.sign.api.commons.controller.BaseController;
import cn.maidaotech.edu.sign.api.commons.util.RandomValidateCodeUtil;
import cn.maidaotech.edu.sign.api.support.model.VCode;
import cn.maidaotech.edu.sign.api.support.service.VCodeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">河南迈道科技有限公司</a>(like)
 * @create: 2019-06-13 09:54
 **/
@Controller
@RequestMapping("/support/vcode")
public class VCodeController extends BaseController {

    @Autowired
    private VCodeServiceImp vCodeService;

    @Action(session = ActionSession.NONE)
    @RequestMapping(value = "/vcode")
    public void vcode(HttpServletRequest request, HttpServletResponse response, Long key){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
        String randomString = randomValidateCode.getRandcode(request, response, key);//输出验证码图片方法
        vCodeService.saveVCode(new VCode(key,randomString));
    }
}
