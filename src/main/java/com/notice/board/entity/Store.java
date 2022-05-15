package com.notice.board.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity    // DB에 있는 테이블을 의미한다라는 것
@Data
public class Store{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeid;

    private String storename;
    private String storelocation;
    private String storemenu;
    private int storeprice;
    private String storetag;
    private String storefilename;
    private String storefilepath;
    private String storescore;
}
