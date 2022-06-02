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
    Page<Total> selectAllSQL(String subject, Pageable pageable);



    // 숙소 게시판
    @Query(value = "select *from total where category=?", nativeQuery = true)
    Page<Total> selectlodging(String subject, Pageable pageable);

    // 맛집 게시판
    @Query(value = "select *from total where category=?", nativeQuery = true)
    Page<Total> selectstore(String subject, Pageable pageable);


  //    List<Total> findByTotallocationContains(String locationword);
   //   List<Total> findByTotalsubjectContains(String subjectword);
  Page<Total> findByTagListContains(@Param("tagList")String tagList,Pageable pageable);

 //   List<Total> findByTotallocation(String locationkey);

    List<Total> findByCategoryAndLocation(@Param("search[0]")String subjectkey,@Param("search[1]")String locationkey);

 //   List<Total> findByTotaltagANDTotallacationANDTotalsubject(String tagkey, String locationkey,String subjectkey);
}
