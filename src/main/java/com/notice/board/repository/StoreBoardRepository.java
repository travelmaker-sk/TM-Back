package com.notice.board.repository;


import com.notice.board.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StoreBoardRepository extends JpaRepository<Store,Object> {
//    Page<Store> findByTitleContaining(String searchkeyword, Pageable pageable);

//    @Modifying
//    @Transactional
//    @Query("update Store s set s.storeviewcount = s.storeviewcount + 1 where s.storeid = :storeid")
//    Integer updateView(Long id);

}
