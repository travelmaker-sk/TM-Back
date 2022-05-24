//package com.notice.board.controller.ApiController;
//
//import com.notice.board.entity.Place;
//import com.notice.board.entity.Store;
//import com.notice.board.service.PlaceBoardService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@RestController      //spring bean 사용하여 따로 설정했으므로 삭제해야함
//@RequestMapping(value="/api/place")
//public class PlaceApiController {
//
//    PlaceBoardService service;
//    @Autowired
//    public PlaceApiController(PlaceBoardService service){         // controller와 service 연결하는 느낌
//
//        this.service = service;
//    }
//
//    // ------------- 숙소 부분 --------------
//
//    // URL 이 변경되지 않은 상태에서 실행
//    @PostMapping("/new")           // 게시글 작성
//    public ResponseEntity<Place> createPlaceData(Place place, MultipartFile file) throws IOException {
//
//        Place write = service.placewrite(place,file);
//
//        return ResponseEntity.ok().body(write);    // 제일 첫 페이지로 돌아감
//    }
//
//    @GetMapping("/findall")                // 게시글 전체 출력
//    public ResponseEntity<List<Place>> findPlaceAll(){
//
//        List<Place> placelist = service.placelist();
//
//        return ResponseEntity.ok().body(placelist);
//    }
//
//    @GetMapping("/find/{id}")           // 게시물 클릭시 세부 페이지 이동
//    public ResponseEntity<Place> findplacedetail(@PathVariable("id") int id){
//
//        Place place = service.placedetail(id);
//        if(place == null){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok().body(place);
////        return new ResponseEntity<>(place, HttpStatus.OK);
//    }
//
//    @GetMapping("/delete/{id}")         // 게시물 삭제
//    public ResponseEntity<Void> Placedelete(@PathVariable int id){
//
//        service.placedelete(id);
//        return ResponseEntity.ok().body(null);
//    }
//
//    @GetMapping("/modify/{id}")      // 게시물 수정을 위한 이전 데이터 호출
//    public ResponseEntity<Place> Placemodify(@PathVariable("id") int id){
//        Place modify = service.placedetail(id);
//
//        return ResponseEntity.ok().body(modify);
//    }
//
//    @PostMapping("/update/{id}")        // 게시물 수정
//    public ResponseEntity<Place> Placeupdate(@PathVariable("id")int id, Place place){
//        Place placetemp = service.placedetail(id);
//
//
//        placetemp.setPlacename(place.getPlacename());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
//        placetemp.setPlacelocation(place.getPlacelocation());
//        placetemp.setPlaceweather(place.getPlaceweather());
//        placetemp.setPlacescore(place.getPlacescore());
//        placetemp.setPlacetag(place.getPlacetag());
//        placetemp.setPlacememo(place.getPlacememo());
//
//        Place edit = service.placeeditwrite(placetemp);
//        return  ResponseEntity.ok().body(edit);
//    }
//}
