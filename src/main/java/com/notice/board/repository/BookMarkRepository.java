package com.notice.board.repository;

import com.notice.board.entity.Bookmark;
import com.notice.board.entity.Liked;
import com.notice.board.entity.Total;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<Bookmark,Object> {


    public Bookmark findByTotalidAndUsername(int id, String name); // where name = ? and ranking = ?

    public List<Bookmark> findByTotalidAndBookmark(int id, String result);

    public boolean existsByTotalidAndUsername(int id,String name);

    @Query(value = "select idx from bookmark where username =? and point=?", nativeQuery = true)
    List<Bookmark> mypagebook(String name,String bookmark);

}
