package com.hab_day.springinit.web.dto;

import com.hab_day.springinit.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor//빈 생성자 꼭 있어야 함
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String author;
    @Builder
    public BoardSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
