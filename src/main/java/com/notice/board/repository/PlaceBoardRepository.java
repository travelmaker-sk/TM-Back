package com.notice.board.repository;


import com.notice.board.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceBoardRepository extends JpaRepository<Place,Object> {
}
