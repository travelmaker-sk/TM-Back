package com.notice.board.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;


    private int totalid;
    private String point;

    private String username;
    private LocalDate  regdate;    // 작성자가 여행한 날짜

    @PrePersist
    public void localAboutDate(){
        this.regdate = LocalDate.now();
        //this.date = String.valueOf(LocalDate.now());
    }
}
