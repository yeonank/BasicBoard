package com.hab_day.springinit.service;


import com.hab_day.springinit.domain.board.BoardRepository;
import com.hab_day.springinit.web.dto.BoardListResponseDto;
import com.hab_day.springinit.web.dto.BoardSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    @Transactional
    public Long save(BoardSaveRequestDto requestDto){
        return boardRepository.save(requestDto.toEntity()).getId();
    }//entity인 reservation을 만들어서 리포지토리에 이 엔티티를 넣는다. 생성된 엔티티의 id를 getter로 받아옴.

    @Transactional(readOnly = true)//조회만 남겨 조회 속도 개선
    public List<BoardListResponseDto> findAllDesc(){
        return boardRepository.findAllDesc().stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }
}

