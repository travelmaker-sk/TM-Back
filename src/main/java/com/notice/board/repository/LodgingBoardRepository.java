package com.notice.board.repository;


import com.notice.board.entity.Lodging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LodgingBoardRepository extends JpaRepository<Lodging,Object> {
}
