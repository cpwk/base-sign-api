package cn.maidaotech.edu.sign.api.user.controller;

import cn.maidaotech.edu.sign.api.commons.context.Contexts;
import cn.maidaotech.edu.sign.api.commons.controller.Action;
import cn.maidaotech.edu.sign.api.commons.controller.ActionSession;
import cn.maidaotech.edu.sign.api.commons.controller.BaseController;
import cn.maidaotech.edu.sign.api.user.model.User;
import cn.maidaotech.edu.sign.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-16 16:24
 **/
@Controller
@RequestMapping(value = "/usr/user")
public class UsrUserController extends BaseController {

    @Autowired
    private UserService userService;

    @Action(session = ActionSession.NONE)
    @RequestMapping(value = "/sign_up")
    public ModelAndView sign_up(String user, Long key) throws Exception {
        User model = parseModel(user, new User());
        userService.signUp(model, key);
        return feedback(model);
    }

    @Action(session = ActionSession.NONE)
    @RequestMapping(value = "/sign_in")
    public ModelAndView sign_in(String username, String password) throws Exception {
        return feedback(userService.signIn(username, password, getRemoteAddress()));
    }

    @Action(session = ActionSession.USER)
    @RequestMapping(value = "/temp")
    public ModelAndView temp() {
        System.out.println(Contexts.sessionUserId());

        return feedback();
    }
}
