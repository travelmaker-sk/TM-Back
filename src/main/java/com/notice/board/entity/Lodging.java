package com.notice.board.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity    // DB에 있는 테이블을 의미한다라는 것
@Data
public class Lodging{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lodgingid;

    private String lodgingname;
    private String lodginglocation;
    private int lodgingprice;
    private String lodgingscore;
    private String lodgingmemo;
    private String lodgingtag;
    private String lodgingfilename;
    private String lodgingfilepath;
}
