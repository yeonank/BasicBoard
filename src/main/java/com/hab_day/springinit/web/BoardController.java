package com.hab_day.springinit.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BoardController {//게시판에서 이어지는 컨트롤러
    @GetMapping("/board/form")
    public String boardForm(Model model){
        //model.addAttribute("userName", user.getName());
        return "freeboard-save";
    }
}
