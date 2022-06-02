package com.notice.board.service;

import com.notice.board.entity.Liked;
import com.notice.board.entity.Total;
import com.notice.board.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service     //spring bean 사용하여 따로 설정했으므로 삭제해야함
public class LikeService {

    @Autowired
    private final LikeRepository repository;

    public LikeService(LikeRepository repository){
        this.repository = repository;
    }

    // 좋아요 데이터 저장
    public void likesave(Liked liked){
        repository.save(liked);
    }


    public Liked like(int id, String name){
        return repository.findByTotalidAndUsername(id,name);
    }

    // 전체 갯수
    public List<Liked> findByTotalidAndPoint(int id, String result){
        return repository.findByTotalidAndPoint(id,result);
    }
    
    // 수정용
    public Liked likefindByTotalid(int id,String name){
        return repository.findByTotalidAndUsername(id,name);
    }

    public boolean check(int id,String name){
        return repository.existsByTotalidAndUsername(id,name);
    }

}
