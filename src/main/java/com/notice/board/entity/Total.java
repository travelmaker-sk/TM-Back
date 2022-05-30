package com.notice.board.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Total {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;


    private int price;
    private int viewcount;

    private String score;
    private String title;
    private String location;
    private String memo;
    private String tag;
    private String filename;
    private String date;
    private String liked;
    private String username;
    private String useremail;
    private String profileimage;
    private String category;
    private LocalDate createdate;    // 작성자가 여행한 날짜
    private String weather;
    private String menu;


    @PrePersist
    public void localAboutDate(){
        this.createdate = LocalDate.now();
        //this.date = String.valueOf(LocalDate.now());
    }



//    @Builder
//    public Total(int totalid, String totalname, String totalsubject, String totallocation, String totalmenu,int totalprice,
//                 String totaltag, String totalfile, String totalscore, int totalviewcount, String totalbookmark){
//        this.totalid = totalid;
//        this.totalname = totalname;
//        this.totallocation = totallocation;
//        this.totalmenu = totalmenu;
//        this.totalprice =totalprice;
//        this.totaltag = totaltag;
//        this.totalfile = totalfile;
//        this.totalscore = totalscore;
//        this.totalviewcount = totalviewcount;
//        this.totalbookmark = totalbookmark;
//    }

//    @Builder
//    public Total(String totaltag){
//        this.totaltag = totaltag;
//    }
}