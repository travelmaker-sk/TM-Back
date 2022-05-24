package com.notice.board.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity    // DB에 있는 테이블을 의미한다라는 것
@Data
public class Place{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placeid;

    private String placelocation;
    private String placename;
    private Date placedate = new Date(System.currentTimeMillis());
    private String placeweather;
    private String placescore;
    private String placememo;
    private String placetag;
    private String placefilename;
    private String placefilepath;
}
