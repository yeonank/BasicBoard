package com.hab_day.springinit.domain.board;

import com.hab_day.springinit.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor//생성자 생성
@Entity//테이블과 링크될 크래스
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동
    private Long id;

    @Column(length = 500, nullable = false)//자동이지만 추가 옵션 넣을 때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Board(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //update 할 장
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
