package com.notice.board.controller.ApiController;

import com.notice.board.entity.Store;
import com.notice.board.service.StoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController      //spring bean 사용하여 따로 설정했으므로 삭제해야함
@RequestMapping(value="/api/store")
public class StoreApiController {

    StoreBoardService service;
    @Autowired
    public StoreApiController(StoreBoardService service){         // controller와 service 연결하는 느낌

        this.service = service;
    }

    // ----- 맛집 부분 ------
    @GetMapping("/find/{id}")
    public ResponseEntity<Store> findstoredetail(@PathVariable("id") int id){

        Store store = service.storedetail(id);
        if(store == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(store);
//        return new ResponseEntity<>(store, HttpStatus.OK);
    }


    // 전체 게시물 출력
    @GetMapping("/findall")                // 게시글 전체 출력
    public ResponseEntity<List<Store>> findStoreAll(){

        List<Store> storelist = service.storelist();

        return ResponseEntity.ok().body(storelist);
//        return new ResponseEntity<>(service.storelist(), HttpStatus.OK);
    }

    // 게시물 삭제
    @GetMapping("/delete/{id}")         // 게시물 삭제
    public ResponseEntity<Void> Storedelete(@PathVariable int id){

        service.storedelete(id);
        return ResponseEntity.ok().body(null);
    }


    @PostMapping("/new")           // 게시글 작성
    public ResponseEntity<Store> createStoreData(Store store,MultipartFile file) throws IOException {

        Store write =service.storewrite(store,file);

        return ResponseEntity.ok().body(write);
    }

    @GetMapping("/modify/{id}")            // 게시물 수정을 위한 이전 데이터 호출
    public ResponseEntity<Store> Storemodify(@PathVariable("id") int id){
        Store modify = service.storedetail(id);

        return ResponseEntity.ok().body(modify);
    }

    @PostMapping("/update/{id}")        // 게시물 수정
    public ResponseEntity<Store> Storeupdate(@PathVariable("id")int id, Store store){
        Store storetemp = service.storedetail(id);


        storetemp.setStorename(store.getStorename());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        storetemp.setStorelocation(store.getStorelocation());
        storetemp.setStoremenu(store.getStoremenu());
        storetemp.setStoreprice(store.getStoreprice());
        storetemp.setStorescore(store.getStorescore());
        storetemp.setStoretag(store.getStoretag());

        Store edit = service.storeeditwrite(storetemp);
        return  ResponseEntity.ok().body(edit);
    }

}
