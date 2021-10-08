package com.example.security2.controller;

import com.example.security2.config.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class BaseController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logoutAction")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("logout");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }

    @GetMapping(value = "/main")
    public String main(Authentication authentication, ModelMap modelMap) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        log.info("securityUser: {}", securityUser);

        modelMap.addAttribute("user", securityUser);

        return "main";
    }

    @GetMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping(value = "/member")
    public String member() {
        return "member";
    }

    @GetMapping(value = "/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping(value = "/admin")
    public String admin() {
        return "admin";
    }
}
