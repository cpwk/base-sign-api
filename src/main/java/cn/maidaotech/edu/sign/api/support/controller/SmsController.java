package cn.maidaotech.edu.sign.api.support.controller;

import cn.maidaotech.edu.sign.api.commons.controller.Action;
import cn.maidaotech.edu.sign.api.commons.controller.ActionSession;
import cn.maidaotech.edu.sign.api.commons.controller.BaseController;
import cn.maidaotech.edu.sign.api.support.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-13 19:44
 **/
@Controller
@RequestMapping("/support/sms")
public class SmsController extends BaseController {
    @Autowired
    private SmsService smsService;

    @Action(session = ActionSession.NONE)
    @RequestMapping("/phone_vcode")
    public ModelAndView phone_vcode(String mobile) throws Exception {
        smsService.sendVcode(mobile);
        return feedback();
    }
}
