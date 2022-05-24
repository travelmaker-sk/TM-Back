//package com.notice.board.controller.ApiController;
//
//import com.notice.board.entity.Lodging;
//import com.notice.board.entity.Store;
//import com.notice.board.service.LodgingBoardService;
//import com.notice.board.service.StoreBoardService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//
//@RestController      //spring bean 사용하여 따로 설정했으므로 삭제해야함
//@RequestMapping(value="/api/lodging")
//public class LodgingApiController {
//
//    LodgingBoardService service;
//    @Autowired
//    public LodgingApiController(LodgingBoardService service){         // controller와 service 연결하는 느낌
//
//        this.service = service;
//    }
//
//    // ----- 맛집 부분 ------
//    @GetMapping("/find/{id}")
//    public ResponseEntity<Lodging> findlodgingdetail(@PathVariable("id") int id){
//
//        Lodging lodging = service.lodgingdetail(id);
//        if(lodging == null){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok().body(lodging);
////        return new ResponseEntity<>(store, HttpStatus.OK);
//    }
//
//
//    // 전체 게시물 출력
//    @GetMapping("/findall")                // 게시글 전체 출력
//    public ResponseEntity<List<Lodging>> findLodgingAll(){
//
//        List<Lodging> lodginglist = service.lodginglist();
//
//        return ResponseEntity.ok().body(lodginglist);
////        return new ResponseEntity<>(service.lodginglist(), HttpStatus.OK);
//    }
//
//    // 게시물 삭제
//    @GetMapping("/delete/{id}")         // 게시물 삭제
//    public ResponseEntity<Void> Lodgingdelete(@PathVariable int id){
//
//        service.lodgingdelete(id);
//        return ResponseEntity.ok().body(null);
//    }
//
//
//    @PostMapping("/new")           // 게시글 작성
//    public ResponseEntity<Lodging> createLodgingData(Lodging lodging,MultipartFile file) throws IOException {
//
//        Lodging write =service.lodgingwrite(lodging,file);
//
//        return ResponseEntity.ok().body(write);
//    }
//
//    @GetMapping("/modify/{id}")            // 게시물 수정을 위한 이전 데이터 호출
//    public ResponseEntity<Lodging> Lodgingmodify(@PathVariable("id") int id){
//        Lodging modify = service.lodgingdetail(id);
//
//        return ResponseEntity.ok().body(modify);
//    }
//
//    @PostMapping("/update/{id}")        // 게시물 수정
//    public ResponseEntity<Lodging> Lodgingupdate(@PathVariable("id")int id, Lodging lodging){
//        Lodging lodgingtemp = service.lodgingdetail(id);
//
//        lodgingtemp.setLodgingname(lodging.getLodgingname());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
//        lodgingtemp.setLodginglocation(lodging.getLodginglocation());
//        lodgingtemp.setLodgingprice(lodging.getLodgingprice());
//        lodgingtemp.setLodgingscore(lodging.getLodgingscore());
//        lodgingtemp.setLodgingmemo(lodging.getLodgingmemo());
//        lodgingtemp.setLodgingtag(lodging.getLodgingtag());
//
//        Lodging edit = service.lodgingeditwrite(lodgingtemp);
//        return  ResponseEntity.ok().body(edit);
//    }
//
//}
