package com.notice.board.controller;

import com.notice.board.entity.Store;
import com.notice.board.service.StoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController      //spring bean 사용하여 따로 설정했으므로 삭제해야함
@RequestMapping(value="/store")
public class StoreBoardController {

    StoreBoardService service;
    @Autowired
    public StoreBoardController(StoreBoardService service){         // controller와 service 연결하는 느낌

        this.service = service;
    }


    // ------------- 맛집 부분 --------------
    @GetMapping("/new")
    public String createStore(){

        return "store/Storecreate";
    }

    // URL 이 변경되지 않은 상태에서 실행
    @PostMapping("/new")           // 게시글 작성
    public String createStoreData(Store store,MultipartFile file) throws IOException {

        service.storewrite(store,file);
        return "redirect:/ ";    // 제일 첫 페이지로 돌아감
    }

    @GetMapping("/findall")                // 게시글 전체 출력
    public String findStoreAll(Model model){
        model.addAttribute("list",service.storelist());

        return "store/Storefindall";
    }

    @GetMapping("/finddetail")         // 게시물 클릭시 세부 페이지 이동
    public String findStoredetail(Model model, int id){

        model.addAttribute("data",service.storedetail(id));
        return "store/Storefinddetail";
    }

    @GetMapping("/delete")         // 게시물 삭제
    public String Storedelete(int id){
        service.storedelete(id);
        return "redirect:/store/findall";
    }

    @GetMapping("/modify/{id}")            // 게시물 수정을 위한 수정 form
    public String Storemodify(@PathVariable("id") int id,Model model){
        model.addAttribute("data", service.storedetail(id));
        return "store/Storemodify";
    }

    @PostMapping("/update/{id}")        // 게시물 수정
    public String Storeupdate(@PathVariable("id")int id, Store store){
        Store storetemp = service.storedetail(id);

        storetemp.setStorename(store.getStorename());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        storetemp.setStorelocation(store.getStorelocation());
        storetemp.setStoremenu(store.getStoremenu());
        storetemp.setStoreprice(store.getStoreprice());
        storetemp.setStorescore(store.getStorescore());
        storetemp.setStoretag(store.getStoretag());

        service.storeeditwrite(storetemp);
        return "redirect:/store/findall";
    }

    /*@PostMapping("/update/{id}")           // 게시물 수정 페이지
    public String Storeupdate(@PathVariable("id") int id,Store store,MultipartFile file) throws IOException {

        Store storetemp = service.storedetail(id);          // 기존의 내용
     //   storetemp.setStorefilepath(store.getStorefilepath());
        storetemp.setStorename(store.getStorename());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        storetemp.setStorelocation(store.getStorelocation());
        storetemp.setStoremenu(store.getStoremenu());
        storetemp.setStoreprice(store.getStoreprice());
        storetemp.setStorescore(store.getStorescore());
        storetemp.setStoretag(store.getStoretag());

        service.storewrite(storetemp,file);

        return "redirect:/store/findall";

    } */
}
