package com.notice.board.repository;

import com.notice.board.entity.Total;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TotalBoardRepository extends JpaRepository<Total,Object> {
//    Page<Store> findByTitleContaining(String searchkeyword, Pageable pageable);

//    @Modifying
//    @Transactional
//    @Query("update Store s set s.storeviewcount = s.storeviewcount + 1 where s.storeid = :storeid")
//    Integer updateView(Long id);

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
  Page<Total> findByTagContains(@Param("tag")String tag,Pageable pageable);

 //   List<Total> findByTotallocation(String locationkey);

 //   List<Total> findByTotalsubjectANDTotallocation(String subjectkey,String locationkey);

 //   List<Total> findByTotaltagANDTotallacationANDTotalsubject(String tagkey, String locationkey,String subjectkey);
}
