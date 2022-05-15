package com.notice.board.repository;


import com.notice.board.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreBoardRepository extends JpaRepository<Store,Object> {
}
