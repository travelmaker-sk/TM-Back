package com.notice.board.repository;

//import spring.basic.demo.domain.Data;

import com.notice.board.entitiy.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Store,Object> {
}
