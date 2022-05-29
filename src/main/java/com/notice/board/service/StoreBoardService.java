package com.notice.board.service;

import com.notice.board.entity.Store;
import com.notice.board.repository.StoreBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service     //spring bean 사용하여 따로 설정했으므로 삭제해야함
public class StoreBoardService {

    @Autowired
    private StoreBoardRepository repository;

    public StoreBoardService(StoreBoardRepository repository){
        this.repository = repository;
    }

    public Store storewrite(Store store, MultipartFile file) throws IOException {


        if(file.isEmpty()) {
            Store save = repository.save(store);
            return save;
        }else{
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\store";        // 파일 경로 저장

            UUID uuid = UUID.randomUUID();      // 식별자 , 파일 이름을 랜덤으로 저장하기 위해

            String filename = uuid + "_" + file.getOriginalFilename();      // 파일 이름 변수에 랜덤이름 저장 => uuid(랜덤 이름)_업로드한파일명

            File saveFile = new File(projectPath, filename);   // projectpath 경로에 name이름으로 저장할것

            file.transferTo(saveFile);

            store.setStorefilename(filename);                   // 파일 이름  db 저장
            store.setStorefilepath("/files/store/" + filename);         // 파일 경로 db 저장
            Store save = repository.save(store);
        return save;}
    }



    public Store storeeditwrite(Store store){
       Store edit = repository.save(store);
        return edit;
    }


    public Page<Store> storelist(Pageable pageable){
        return repository.findAll(pageable);
    }
    // 게시물 세부 정보 확인용
    public Store storedetail(int id){
        return repository.findById(id).get();
    }

    // 조회수 확인용
    public Store storeview(long id){
        return repository.findById(id).get();
    }
    // 게시물 수정 용
    public Store storeeditdetail(int id){
        return repository.findById(id).get();
    }

    public void storedelete(int id){
        repository.deleteById(id);
    }

    // 검색 기능
//    public Page<Store> storesearchlist(String searchKeyword, Pageable pageable){
//        return repository.findByTitleContaining(searchKeyword,pageable);
//    }

//    @Transactional
//    public int updateView(Long id) {
//        return repository.updateView(id);
//    }

}
