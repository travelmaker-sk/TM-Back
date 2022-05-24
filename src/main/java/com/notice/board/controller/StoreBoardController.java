package com.notice.board.controller;

import com.notice.board.entity.Store;
import com.notice.board.service.StoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller      //spring bean 사용하여 따로 설정했으므로 삭제해야함
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
    public String findStoreAll(Model model, @PageableDefault(page = 0, size = 15, sort="storeid", direction = Sort.Direction.DESC)
            Pageable pageable, String searchKeyword){

        Page<Store> list = null;

//        if(searchKeyword == null){
//            list = service.storelist(pageable);
//        }else{
//            list = service.storesearchlist(searchKeyword,pageable);
//        }

        list = service.storelist(pageable);         // 페이지마다 설정한 갯수의 게시물 출력
        int nowpage = list.getPageable().getPageNumber() +1;    // 게시물 아래의 페이지 번호 구현을 위한 설정 - 현재 페이지
        int startpage = Math.max(nowpage -4,1);                 // 현재 페이지 기준으로 앞쪽으로 4개의 페이지 출력
                                                                // 만약 현재 페이지가 1이면 -3이 출력되므로 최소 1로 설정
        int endpage = Math.min(nowpage+5,list.getTotalPages()); // 마지막 페이지까지 출력


        model.addAttribute("list",list);
        model.addAttribute("nowpage",nowpage);      // html에서 출력시키기 위해 model을 사용하여  값 전송
        model.addAttribute("startpage",startpage);
        model.addAttribute("endpage",endpage);

        return "store/Storefindall";
    }

    @GetMapping("/finddetail")         // 게시물 클릭시 세부 페이지 이동
    public String findStoredetail(Model model, int id,Store store){
        Store view = service.storedetail(id);
        int result = view.getStoreviewcount();
        view.setStoreviewcount(result+1);

        model.addAttribute("data",service.storedetail(id));

        return "store/Storefinddetail";
    }

//    @GetMapping("/finddetail/{id}")         // 게시물 클릭시 세부 페이지 이동
//    public void viewcount(@PathVariable("id")int id, Model model, Store store){
//        Store view = service.storedetail(id);
//        int result = store.getStoreviewcount();
//        view.setStoreviewcount(result);
//        System.out.println(result);
//    }

//    @GetMapping("/finddetail/{id}")     // 조회수 증가
//    public void read(@PathVariable Long id, Model model) {
//        Store dto = service.storeview(id);
//        service.updateView(id); // views ++
//        model.addAttribute("posts", dto);
//    }


    @GetMapping("/delete")         // 게시물 삭제
    public String Storedelete(int id){
        service.storedelete(id);
        return "redirect:/store/findall";
    }

    @GetMapping("/modify/{id}")            // 게시물 수정을 위한 수정 form
    public String Storemodify(@PathVariable("id") int id,Model model){
        model.addAttribute("data", service.storeeditdetail(id));
        return "store/Storemodify";
    }



    @PostMapping("/update/{id}")        // 게시물 수정
    public String Storeupdate(@PathVariable("id")int id, Store store){
        Store storetemp = service.storeeditdetail(id);

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

    @GetMapping("/fetch")               // fetch 부분
    public String fetch() {
        return "storefetch";
    }


}
