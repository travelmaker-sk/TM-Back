package com.notice.board.controller.ApiController;

import com.notice.board.entity.Bookmark;
import com.notice.board.entity.Liked;
import com.notice.board.entity.Total;
import com.notice.board.entity.dto.DetailItemDTO;
import com.notice.board.entity.dto.DetailResponse;
import com.notice.board.service.BookMarkService;
import com.notice.board.service.LikeService;
import com.notice.board.service.TotalBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/total/api")
public class ApiController {
    TotalBoardService totalBoardService;
    LikeService likeService;
    BookMarkService bookmarkService;

    @Autowired
    public ApiController(TotalBoardService totalBoardService, LikeService likeService, BookMarkService bookmarkService) {
        this.totalBoardService = totalBoardService;
        this.likeService = likeService;
        this.bookmarkService = bookmarkService;
    }
/////////////////////////////////////////////////////////////////////////////////

//    ///Where = 존재 or null , what = 존재 or null
//    @GetMapping("apitest2")
//    public List<QueryRequestDTO> requestSearch(@RequestParam(required = true, value = "limit")int limit,
//                                               @RequestParam(required = false, value = "where")String where,
//                                               @RequestParam(required = false, value = "what")String what ,
//                                               @PageableDefault(page = 0,size = 10) Pageable pageable){
////        List<QueryRequestDTO> list = new ArrayList<>();
////        QueryRequestDTO requestDTO = new QueryRequestDTO();
////        RestaurantDTO restaurantDTO = new RestaurantDTO();
//
//          Page<Total> entityList = totalBoardService.selectAllSQL(where,what,pag)
//        //페이지 개수 제단하는 것 정하는 부분
//
//        if(where != null && what!=null){ //둘다 값이 있을경우
//
//            Page<Total> list = totalBoardService.selectAllSQL(where,what,pageable);
////            requestDTO.setWhat(what);
////            requestDTO.setWhere(where);
////            list.add(requestDTO);
////            // post 인기순, 최신순, 가볼만한곳,맛집, 숙소 5개 보내기
//            return list;
//        }
//        else if(where == null){ //what값만 있을경우
//            // what = tag 값 조회
//
////            requestDTO.setLimit(limit);
////            requestDTO.setWhat(what);
//
//
//            return  list;
//        }
//        else if(what == null){ //what만 있을경우
//            //where = 장소 값 조회
////            requestDTO.setLimit(limit);
////            requestDTO.setWhere(where);
//            return  list;
//        }
//
//        return list;
//    }

    /*더보기 눌렀을 때
     * 1. 상대편에서는 param으로 where,what,category,sort,page 보냄
     * 2. 그럼 나는 category,sort를 orderby, findby 두개 같이있는 쿼리문에 넣어서 그 값 반환, Entity 매핑
     * 3. Entity -> DTO 한다음 해당 리스트들 16개씩 짤라서 return
     *
     *
     *
     *
     */


//    @GetMapping("/apitest3")
//    public Page<Total> test(@RequestParam String category,
//                            Pageable pageable){
//
//    return totalBoardService.findByCategory(category, pageable);
////
////
//
//    }

    @GetMapping("/apitest1")
    public void postMoreResponse(@RequestParam(value = "where",required = false) String where,
                                   @RequestParam(value = "what",required = false) String what,
                                   @RequestParam(value = "category") String category,
                                   Pageable pageable) {




    }

    @GetMapping("/apitest2")
    public void postSearchResponse(@RequestParam(required = false) String where,
                                 @RequestParam(required = false) String what,
                                 @RequestParam String category,
                                 @RequestParam String sort,
                                 @RequestParam int page ){

    }


    //detail item 받는 API
    @PostMapping("/detailsave")
    public Total saveDetail(@RequestBody DetailItemDTO detailItemDTO,
                                      Total total,
                                      MultipartFile file) throws IOException {
        totalBoardService.totalwrite(detailItemDTO.toEntity(), file);
        return detailItemDTO.toEntity();
    }

    //수정본 받아서 업데이트 하기
    @PostMapping("/update/{id}")
    public DetailItemDTO updateDetail(@PathVariable(value = "id") int id, Total entity,
                                      @RequestBody DetailItemDTO newDetailItemDTO,
                                      MultipartFile file) throws IOException {

        //RequestBody로 부터 받은 Data detailItemDTO에 저장.
        //기존에 {id}에 저장된 정보 Entity 매핑
        entity = totalBoardService.totaleditdetail(id);
        System.out.println("entity" + entity);
        System.out.println("entity에 DB 매핑 성공");
        DetailItemDTO tmpDetailItemDTO = new DetailItemDTO();
        System.out.println("DTO 객체 생성 성공");
        //(기존데이터 들어있는)entity -> DTO 변환 작업
        tmpDetailItemDTO.toDTO(entity);
        //기존데이터 DTO 와 새로운 데이터 교체 작업
        System.out.println("데이터 업데이트 시작");
        tmpDetailItemDTO.setTitle(newDetailItemDTO.getTitle());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        tmpDetailItemDTO.setLocation(newDetailItemDTO.getLocation());
        tmpDetailItemDTO.setMemo(newDetailItemDTO.getMemo());
        tmpDetailItemDTO.setDate(newDetailItemDTO.getDate());
        tmpDetailItemDTO.setWeather(newDetailItemDTO.getWeather());
        tmpDetailItemDTO.setMenu(newDetailItemDTO.getMenu());
        tmpDetailItemDTO.setPrice(newDetailItemDTO.getPrice());
        tmpDetailItemDTO.setScore(newDetailItemDTO.getScore());
        tmpDetailItemDTO.setTagList(newDetailItemDTO.getTagList());
        tmpDetailItemDTO.setCategory(newDetailItemDTO.getCategory()); //근데 이거 .equal로 해서 false나오는 것만 바꾸면 되지 않나..?
        System.out.println("데이터 업데이트 성공");                       //일단 나중에 생각하자 ㅋ.ㅋ
        //다시 교체된 DTO -> Entity 변환
        totalBoardService.totalwrite(tmpDetailItemDTO.toEntity(), file);

        return tmpDetailItemDTO;


    }

    //detail item 보내는 API
    @GetMapping("/detailresponse/{id}")
    public DetailResponse responseDetail(@PathVariable(value = "id") int id,
                                         DetailItemDTO detailItemDTO,
                                         DetailResponse detailResponse,
                                         Bookmark bookmarkEntity,
                                         Liked likedEntity) {
        String name = "홍길동"; //지워라

        bookmarkEntity = bookmarkService.bookmark(id,name);
        likedEntity = likeService.like(id,name);

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        PrincipalDetail principalDetail = (PrincipalDetail)principal;
//        String username = principalDetail.getUsername();

        //like dto, bookmark dto,detail dto 합쳐서 보낼거
        boolean likedCheck = false;
        boolean bookMarkCheck = false;
        boolean existbookMarkCheck = bookmarkService.check(id,name);
        boolean existlikedCheck = likeService.check(id,name);
        List<Liked> likelistSize = likeService.findByTotalidAndPoint(id, name); //좋아요 개수 확인용


        if(existlikedCheck == true)
        {
            ///////////////////DB 값 조회해서 true or false 반납/////////////
            if (likedEntity.getPoint().equals("true"))
                likedCheck = true;
            else if (likedEntity.getPoint().equals("false"))
                likedCheck = false;
        } else likedCheck = false;

        if(existbookMarkCheck == true){
            if(bookmarkEntity.getBookmark().equals("true"))
                bookMarkCheck = true;
            else if(bookmarkEntity.getBookmark().equals("false"))
                bookMarkCheck = false;
        } else bookMarkCheck = false;




        //////////////////////////////////{id}번게시물이 좋아요 눌린 개수 확인///////////////////////////////
        Total totalEntity = totalBoardService.totaldetail(id); //{id}번 게시물 DB -> Entity 매핑
        detailResponse.toDTO(totalEntity); //entity -> dto 매핑
        ////////////////////////Writer(User정보 담아야함 미완성..............//////////////////
        ArrayList<DetailResponse.WriterDTO> writerDTOlist = new ArrayList<>();
        writerDTOlist.add(new DetailResponse.WriterDTO("호랑이", "asd")); //-> 바꿔야할 부분
        detailResponse.setWriter(writerDTOlist);
        /////////////////////////////좋아요 정보 DTO에 저장 ////////////////////////////
        ArrayList<DetailResponse.LikeDTO> likeDTOlist = new ArrayList<>();
        likeDTOlist.add(new DetailResponse.LikeDTO(likelistSize.size(), likedCheck)); //좋아요 수, 좋아요 여부 추가
        detailResponse.setLike(likeDTOlist); // 배열 저장
        detailResponse.setBookmarkCheck(bookMarkCheck); //북마크 여부 저장
        ////////////////조회수 ////////////
        int tmp = detailResponse.getViewCount();
        tmp++;
        detailResponse.setViewCount(tmp);
        totalEntity.setViewcount(detailResponse.getViewCount());
        //////////////////////////////////
        totalBoardService.save(totalEntity);

//        System.out.println("list" + detailResponse.getWriter());

        //////////////////////////////////////////////////////////////////////////////////
        return detailResponse;
    }


    //{id}번 게시물 삭제요!
    @GetMapping("/delete/{id}")         // 게시물 삭제
    public String deleteDetail(@PathVariable int id) {
        totalBoardService.totaldelete(id);

        return "게시물이 삭제되었습니다.";

    }

    // 좋아요 누르면 저장되는 API (게시물 item 아님)
    @GetMapping("/liked/{id}")
    public boolean likeSave(@PathVariable(value = "id") int id,
                            Liked likedEntity) {

        //세션 연결용 아이디
        String name = "홍길동";
        boolean likeCheck = likeService.check(id, name);
        boolean checkResult = false;
        List<Liked> likelist = likeService.findByTotalidAndPoint(id, "true");

        int size = likelist.size();

        System.out.println("size :" + size);


        //DB저장
        if (likeCheck != false) {
            likedEntity.setTotalid(id);
            likedEntity.setUsername(name);
            Liked datalist = likeService.like(id, name);
            if (datalist.getPoint().equals(("false"))) {
                Liked temp = likeService.likefindByTotalid(id, name);
                likedEntity.setPoint(("true"));
                temp.setPoint(likedEntity.getPoint());
                likeService.likesave(temp);
            } else if (datalist.getPoint().equals("true")) {
                Liked temp = likeService.likefindByTotalid(id, name);
                likedEntity.setPoint(("false"));
                temp.setPoint(likedEntity.getPoint());
                likeService.likesave(temp);
            }
        } else {
            likedEntity.setTotalid(id);
            likedEntity.setUsername(name);
            likedEntity.setPoint("false");
            likeService.likesave(likedEntity);
        }

        if(likedEntity.getPoint().equals("true"))
            checkResult = true;
        else if(likedEntity.getPoint().equals("false"))
            checkResult = false;

        System.out.println("likeCheck"+ likedEntity.getPoint());


        return checkResult;
    }

    @GetMapping("/bookmark/{id}")         // 게시물 북마크
    public boolean bookmarkSave(@PathVariable(value = "id") int id, Bookmark bookmarkEntity) {

       // 나중에 바꿀꺼

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        PrincipalDetail principalDetail = (PrincipalDetail)principal;
//        String username = principalDetail.getUsername();

        String name = "홍길동";// username으로 바꿔라 세션 연결전 아이디 디폴트값
        boolean result = bookmarkService.check(id,name);     // db에 데이터가 있는지 유무 확인
        boolean bookmarkCheck = false;


        if(result != false){
            bookmarkEntity.setTotalid(id);
            bookmarkEntity.setUsername(name);
            Bookmark datalist = bookmarkService.bookmark(id,name);       // where totalid and username을 통한 값 저장
            if(datalist.getBookmark().equals("false")){
                Bookmark temp = bookmarkService.bookmarkfindByTotalid(id,name);
                bookmarkEntity.setBookmark("true");
                temp.setBookmark(bookmarkEntity.getBookmark());
                bookmarkService.bookmarksave(temp);
            }else if(datalist.getBookmark().equals("true")){
                Bookmark temp = bookmarkService.bookmarkfindByTotalid(id,name);
                bookmarkEntity.setBookmark("false");
                temp.setBookmark(bookmarkEntity.getBookmark());
                bookmarkService.bookmarksave(temp);
            }
        }else{
            bookmarkEntity.setTotalid(id);
            bookmarkEntity.setUsername(name);
            bookmarkEntity.setBookmark("true");
            bookmarkService.bookmarksave(bookmarkEntity);
        }

        if(bookmarkEntity.getBookmark().equals("true"))
            bookmarkCheck = true;
        else if(bookmarkEntity.getBookmark().equals("false"))
            bookmarkCheck = false;

        System.out.println("check:"+ bookmarkEntity.getBookmark());

        return bookmarkCheck;
    }


}