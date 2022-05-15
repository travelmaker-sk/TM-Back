package com.notice.board.controller;

import com.notice.board.entity.Store;
import com.notice.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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

        return "store/Storecreate";
    }

    // URL 이 변경되지 않은 상태에서 실행
    @PostMapping("store/new")           // 게시글 작성
    public String createStoreData(Store store, MultipartFile file) throws IOException {

        service.storewrite(store,file);
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
        return "redirect:/store/findall";
    }

    @GetMapping("store/modify/{id}")            // 게시물 수정
    public String Storemodify(@PathVariable("id") int id,Model model){
        model.addAttribute("data", service.storedetail(id));
        return "store/Storemodify";
    }

    @PostMapping("store/update/{id}")           // 게시물 수정 페이지
    public String Storeupdate(@PathVariable("id") int id,Store store,MultipartFile file) throws IOException {

        Store storetemp = service.storedetail(id);          // 기존의 내용
        storetemp.setStorename(store.getStorename());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        storetemp.setStorelocation(store.getStorelocation());
        storetemp.setStoremenu(store.getStoremenu());
        storetemp.setStoreprice(store.getStoreprice());
        storetemp.setStoretag(store.getStoretag());

        service.storewrite(storetemp,file);

        return "redirect:/store/findall";

    }
}
