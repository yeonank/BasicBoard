package com.hab_day.springinit.web.dto;

import com.hab_day.springinit.domain.board.Board;
import com.hab_day.springinit.domain.reservation.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public BoardListResponseDto(Board entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
