package com.hab_day.springinit.web;


import com.hab_day.springinit.service.BoardService;
import com.hab_day.springinit.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class NavbarController {
    public final ReservationService reservationService;
    public final BoardService boardService;

    @GetMapping("/reservation/list")
    public String reserveLists(Model model){
        model.addAttribute("reservations", reservationService.findAllDesc());
        return "reservation-lists";
    }//html이면 검색 결과를 json으로 response 넘겨서 그 response값을 가지고 브라우저에서 화면을 그린다.
    //머스타치에서는 서버에서 문서에 값을 넣어서 전달.

    @GetMapping("/reservation/form")
    public String reserveForm(Model model){
        //model.addAttribute("userName", user.getName());
        return "reservation-save";
    }
    @GetMapping("/board/list")
    public String boardLists(Model model){
        model.addAttribute("posts", boardService.findAllDesc());
        return "freeboard-lists";
    }
}
