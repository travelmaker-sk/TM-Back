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
    private int totalid;


    private String totalname;
    private String totalsubject;
    private String totallocation;
    private String totalmenu;
    private int totalprice;
    private Date totaltraveldate;       // 작성자가 여행한 날짜
    private String totaltag;
    private String totalscore;
    private int totalviewcount;
    private String totalbookmark;
    private LocalDate totaldate;
    private String totalfile;
    private String totalwriter;

    @PrePersist
    public void localAboutDate(){
        this.totaldate = LocalDate.now();
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