package com.notice.board.repository;


import com.notice.board.entity.Store;
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

    @Query(value = "select *from total where totalsubject=?", nativeQuery = true)
    Page<Total> selectAllSQL(String subject, Pageable pageable);

//    @Query(value = "update total set totalbookmark=? where totalid=?", nativeQuery = true)
  //  String like(@Param("bookmark")String bookmark,@Param("id") int id);

//    @Query(value = "select *from total where totaltag=?", nativeQuery = true)
//    List<Total> findtaglist(String keyword);

      List<Total> findByTotallocation(String locationkey);
      List<Total> findByTotalsubject(String subjectkey);
      List<Total> findByTotaltagContains(@Param("keyword")String keyword);

 //   List<Total> findByTotallocation(String locationkey);

 //   List<Total> findByTotalsubjectANDTotallocation(String subjectkey,String locationkey);

 //   List<Total> findByTotaltagANDTotallacationANDTotalsubject(String tagkey, String locationkey,String subjectkey);
}
