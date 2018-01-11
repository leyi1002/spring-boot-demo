package com.jay.boot.controller;

import com.jay.boot.bean.Msg;
import com.jay.boot.bean.SysUser;
import com.jay.boot.dao.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/26.
 */
@Controller
public class UserController {

    @Autowired
    private SysUserDao sysUserDao;

//    @RequestMapping("/login")
//    public String login(){
//        return "login";
//    }

    @RequestMapping("/")
    public String index(Model model){
        Msg msg =  new Msg("测试标题","测试内容","欢迎来到HOME页面,您拥有 ROLE_HOME 权限");
        model.addAttribute("msg", msg);
        return "home";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String hello(){
        return "hello admin";
    }

    @ResponseBody
    @RequestMapping("/read")
    public String login(String username){
        return "read  a";
    }
}
