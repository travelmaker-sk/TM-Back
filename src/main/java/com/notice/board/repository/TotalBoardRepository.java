package com.notice.board.repository;

import com.notice.board.entity.Liked;
import com.notice.board.entity.Total;
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
public interface TotalBoardRepository extends JpaRepository<Total,Object> {

    // 조회수
//    @Query(value = "select viewcount from total where idx=?", nativeQuery = true)
   // Total viewcount(int id);
    @Transactional
    @Modifying
    @Query("update Total t set t.viewcount = t.viewcount + 1 where t.idx = :idx ")
    int updateview(@Param("id") int id);

    // 명소 게시판
    @Query(value = "select *from total where category=?", nativeQuery = true)
    Page<Total> selectAllSQL(@Param("subject")String subject, Pageable pageable);

    // 숙소 게시판
    @Query(value = "select *from total where category=?", nativeQuery = true)
    Page<Total> selectlodging(String subject, Pageable pageable);

    // 맛집 게시판
    @Query(value = "select *from total where category=?", nativeQuery = true)
    Page<Total> selectstore(String subject, Pageable pageable);

    // 마이페이지 자신이 작성한 게시물
    @Query(value = "select *from total where location=? and username=? order by idx ", nativeQuery = true)
    List<Total> mypage(String location,String name);

  Page<Total> findByTagListContains(@Param("what")String searchtag,Pageable pageable);

  Page<Total> findByLocationContains(@Param("where")String searchlocation,Pageable pageable);

  Page<Total> findByLocationAndTagListContains(@Param("what")String searchtag,@Param("where")String searchlocation,Pageable pageable);
}
