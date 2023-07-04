package com.hab_day.springinit.web.api;

import com.hab_day.springinit.service.BoardService;
import com.hab_day.springinit.web.dto.BoardSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("api/board")
    public Long save(@RequestBody BoardSaveRequestDto requestDto){
        return boardService.save(requestDto);
    }
}
