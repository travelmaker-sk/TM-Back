package com.notice.board.repository;

import com.notice.board.entity.Liked;
import com.notice.board.entity.Total;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Liked,Object> {


    public Liked findByTotalidAndUsername(int id, String name); // where name = ? and ranking = ?

    public List<Liked> findByTotalidAndPoint(int id, String result);

    public boolean existsByTotalidAndUsername(int id,String name);


}
