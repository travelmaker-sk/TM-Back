package com.notice.board.controller;

import com.notice.board.entitiy.Store;
import com.notice.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;


@Controller      //spring bean 사용하여 따로 설정했으므로 삭제해야함
public class BoardController {

    BoardService service;
    @Autowired
    public BoardController(BoardService service){         // controller와 service 연결하는 느낌

        this.service = service;
    }

    // ------------- 맛집 부분 --------------
    @GetMapping("store/new")
    public String createStore(){

        return "store/StorecreateForm";
    }

    // URL 이 변경되지 않은 상태에서 실행
    @PostMapping("store/new")           // 게시글 작성 
    public String createStoreData(Store store){

       service.storewrite(store);
        return "redirect:/";    // 제일 첫 페이지로 돌아감
    }

    @GetMapping("store/findall")                // 게시글 전체 출력
    public String findStoreAll(Model model){
        model.addAttribute("list",service.storelist());

        return "store/Storefindall";
    }

    @GetMapping("store/finddetail")         // 게시물 클릭시 세부 페이지 이동
    public String findStoredetail(Model model, int id){

        model.addAttribute("data",service.storedetail(id));
        return "store/Storefinddetail";
    }

    @GetMapping("store/delete")         // 게시물 삭제
    public String Storedelete(int id){
        service.storedelete(id);
        return "store/findall";
    }
}
