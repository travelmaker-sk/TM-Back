package com.notice.board.service;

import com.notice.board.entity.Liked;
import com.notice.board.entity.Store;
import com.notice.board.entity.Total;
import com.notice.board.repository.StoreBoardRepository;
import com.notice.board.repository.TotalBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service     //spring bean 사용하여 따로 설정했으므로 삭제해야함
public class TotalBoardService {

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    private TotalBoardRepository repository;

    public TotalBoardService(TotalBoardRepository repository){
        this.repository = repository;
    }

    public void totalwrite(Total total, MultipartFile file) throws IOException {
        //파일의 저장경로를 위한 경로설정
//        boolean noneFIle = file.isEmpty();

        if (file != null) {
            System.out.println("파일 업로드");
            String path = fileDir;

            //이미지파일 중복을 방지하기위해 uuid설정
            UUID uuid = UUID.randomUUID();

            //filename의 uuid + 파일명
            String fileName = uuid + "_" + file.getOriginalFilename();


            File saveFile = new File(path, fileName);

            //파일전송
            file.transferTo(saveFile);

            total.setImageUrl(fileName);
            //total.setTotalfile(fileName);

            repository.save(total);
        } else if(file == null) {
            System.out.println("파일 업로드 실패");
            total.setImageUrl(null);
            repository.save(total);
        }
    }


    public Total save(Total total){

        return repository.save(total);
    }

    public Page<Total> totallist(Pageable pageable){
        return repository.findAll(pageable);
    }
    // 게시물 세부 정보 확인용
    public Total totaldetail(int id){
        return repository.findById(id).get();
    }

    // 조회수 확인용
    public Total totalview(long id){
        return repository.findById(id).get();
    }
    // 게시물 수정 용
    public Total totaleditdetail(int id){
        return repository.findById(id).get();
    }

    public void totaldelete(int id){
        repository.deleteById(id);
    }

    // 목적에 맞는 게시물 출력 (명소)
    public Page<Total> selectAllSQL(String subject, Pageable pageable){
        return repository.selectAllSQL(subject, pageable);
    }

//    public Page<Total> selectlodging(String subject, Pageable pageable){
//        return repository.selectlodging(subject, pageable);
//    }
//
//    public Page<Total> selectstore(String subject, Pageable pageable){
//        return repository.selectstore(subject, pageable);
//    }

//    public List<Total> findtaglist(String keyword){
//        return repository.findtaglist(keyword);
//    }

    /*
    @Transactional
    public List<Total> locationsearch(String locationword) {
        List<Total> searchlist = repository.findByTotallocation(locationword);
        return searchlist;
    }

    @Transactional
    public List<Total> subjectsearch(String subjectword) {
        List<Total> searchlist = repository.findByTotalsubject(subjectword);
        return searchlist;
    }
    */
    
    // 태그를 통한 검색
    @Transactional
    public Page<Total> tagsearch(String seactchtag,Pageable pageable) {
        return repository.findByTagListContains(seactchtag,pageable);
    }
    // 지역을 통한 검색
    @Transactional
    public Page<Total> locationsearch(String seactchlocation,Pageable pageable) {
        return repository.findByLocationContains(seactchlocation,pageable);
    }
    // 태그,지역을 통한 검색
    @Transactional
    public Page<Total> totalsearch(String searchtag, String searchlocation, Pageable pageable){
        return repository.findByLocationAndTagListContains(searchtag,searchlocation,pageable);
    }

    public int updateview(int id){
        return repository.updateview(id);
    }
    /*
    @Transactional
    public List<Total> locationsearch(String locationkey) {
        List<Total> searchlist = repository.findByTotallocation(locationkey);
        return searchlist;
    }

    @Transactional
    public List<Total> subjectsearch(String subjectkey,String locationkey) {
        List<Total> searchlist = repository.findByTotalsubjectANDTotallocation(subjectkey,locationkey);
        return searchlist;
    }

    @Transactional
    public List<Total> tagsearch(String tagkey, String locationkey, String subjectkey) {
        List<Total> searchlist = repository.findByTotaltagANDTotallacationANDTotalsubject(tagkey, locationkey, subjectkey);
        return searchlist;
    }
    */
}
