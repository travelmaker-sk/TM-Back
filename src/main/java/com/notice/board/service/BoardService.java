package com.notice.board.service;

import com.notice.board.entity.Store;
import com.notice.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service     //spring bean 사용하여 따로 설정했으므로 삭제해야함
public class BoardService {

    @Autowired
    private BoardRepository repository;

    public BoardService(BoardRepository repository){
        this.repository = repository;
    }

    public void storewrite(Store store){
        repository.save(store);
    }

    public List<Store> storelist(){
        return repository.findAll();
    }

    public Store storedetail(int id){
        return repository.findById(id).get();
    }

    public void storedelete(int id){

        repository.deleteById(id);
    }
}
