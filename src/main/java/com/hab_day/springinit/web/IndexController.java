package com.hab_day.springinit.web;

import com.hab_day.springinit.config.auth.LoginUser;
import com.hab_day.springinit.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor//생성자 자동 주입
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        if (user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
