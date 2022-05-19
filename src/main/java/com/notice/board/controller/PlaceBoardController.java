package com.notice.board.controller;

import com.notice.board.entity.Place;
import com.notice.board.service.PlaceBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController      //spring bean 사용하여 따로 설정했으므로 삭제해야함
@RequestMapping(value="/place")
public class PlaceBoardController {

    PlaceBoardService service;
    @Autowired
    public PlaceBoardController(PlaceBoardService service){         // controller와 service 연결하는 느낌

        this.service = service;
    }

    // ------------- 숙소 부분 --------------
    @GetMapping("/new")
    public String createPlace(){

        return "place/Placecreate";
    }

    // URL 이 변경되지 않은 상태에서 실행
    @PostMapping("/new")           // 게시글 작성
    public String createPlaceData(Place place, MultipartFile file) throws IOException {

        service.placewrite(place,file);
        return "redirect:/";    // 제일 첫 페이지로 돌아감
    }

    @GetMapping("/findall")                // 게시글 전체 출력
    public String findPlaceAll(Model model){
        model.addAttribute("list",service.placelist());

        return "place/Placefindall";
    }


    @GetMapping("/finddetail")         // 게시물 클릭시 세부 페이지 이동
    public String findPlacedetail(Model model, int id){

        model.addAttribute("data",service.placedetail(id));
        return "place/Placefinddetail";
    }

    @GetMapping("/delete")         // 게시물 삭제
    public String Placedelete(int id){
        service.placedelete(id);
        return "redirect:/place/findall";
    }

    @GetMapping("/modify/{id}")            // 게시물 수정
    public String Placemodify(@PathVariable("id") int id,Model model){
        model.addAttribute("data", service.placedetail(id));
        return "place/Placemodify";
    }

    @PostMapping("/update/{id}")
    public String Placeupdate(@PathVariable("id")int id, Place place){

        Place placetemp = service.placedetail(id);

        placetemp.setPlacename(place.getPlacename());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        placetemp.setPlacelocation(place.getPlacelocation());
        placetemp.setPlaceweather(place.getPlaceweather());
        placetemp.setPlacescore(place.getPlacescore());
        placetemp.setPlacetag(place.getPlacetag());
        placetemp.setPlacememo(place.getPlacememo());

        service.placeeditwrite(placetemp);
        return "redirect:/place/findall";
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
