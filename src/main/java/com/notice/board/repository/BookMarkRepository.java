package com.notice.board.repository;

import com.notice.board.entity.Bookmark;
import com.notice.board.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<Bookmark,Object> {


    public Bookmark findByTotalidAndUsername(int id, String name); // where name = ? and ranking = ?

    public List<Bookmark> findByTotalidAndBookmark(int id, String result);

    public boolean existsByTotalidAndUsername(int id,String name);


}
