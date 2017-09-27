package com.chartroom.controller;

import com.chartroom.model.User;
import com.chartroom.service.UserService;
import com.chartroom.util.AjaxResult;
import com.chartroom.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by icinfo on 2017-09-06.
 */
@Controller
@RequestMapping("/chartRoom")
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("/enChartRoom")
    public ModelAndView enChartRoom(String username){
        ModelAndView mv = new ModelAndView("/chart_room");
        mv.addObject("username",username);
        User user = service.getUserByUsername(username);
        mv.addObject("name",user.getName());
        return mv;
    }

    @RequestMapping("/enLogin")
    public String enLogin(){
        return "/login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult checkLogin(@RequestBody User user, HttpSession session){
        if(user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()))
            return AjaxResult.fail("用户名和密码不能为空!");
        String username = user.getUsername();
        String password = user.getPassword();
        boolean canLogin = service.checkLogin(username,password);
        if(canLogin) {
            User temp = this.service.getUserByUsername(username);
            session.setAttribute(StringUtil.USERNAME_KEY,temp);
            return AjaxResult.success(username);
        }
        return AjaxResult.fail("用户名密码错误!");
    }
}
