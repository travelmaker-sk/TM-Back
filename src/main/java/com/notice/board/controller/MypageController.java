/*  package com.notice.board.controller;

import com.notice.board.entity.Bookmark;
import com.notice.board.entity.Liked;
import com.notice.board.entity.Total;
import com.notice.board.service.BookMarkService;
import com.notice.board.service.LikeService;
import com.notice.board.service.TotalBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {

    TotalBoardService service;
    LikeService likeService;
    BookMarkService bookMarkService;

    @Autowired
    public MypageController(TotalBoardService service, LikeService likeService, BookMarkService bookMarkService) {         // controller와 service 연결하는 느낌
        this.service = service;
        this.likeService = likeService;
        this.bookMarkService = bookMarkService;
    }

//    page = 0, size = 9,
    @GetMapping(" ")         // 마이페이지
    public ResponseEntity<List<Total>> Mypage(Model model){

        // 자신이 작성한 게시물 리스트
        String name = "홍길동";

        List<Total> jeju = service.mypage("제주",name);
        List<Total> busan = service.mypage("부산",name);
        List<Total> seoul = service.mypage("서울",name);
        List<Total> incheon = service.mypage("인천",name);
        List<Total> sokcho = service.mypage("속초",name);
        List<Total> gangneung = service.mypage("강릉",name);
        List<Total> yeosu = service.mypage("여수",name);

        System.out.println(jeju);
        System.out.println(busan);
        System.out.println(seoul);
        List mypage = new ArrayList<>();
        mypage.add(jeju);
        mypage.add(busan);
        mypage.add(seoul);
        mypage.add(incheon);
        mypage.add(sokcho);
        mypage.add(gangneung);
        mypage.add(yeosu);

        System.out.println(mypage);

        model.addAttribute("list", mypage);


        // 북마크 리스트
 //       String bookmark = "true";
  //      List<Bookmark> bookmarkList = bookMarkService.mypagebook(name,bookmark);
  //      System.out.println(bookmarkList);


        return ResponseEntity.ok().body(mypage);
//        return new ResponseEntity<>(join, HttpStatus.OK);

    }

}

 */
