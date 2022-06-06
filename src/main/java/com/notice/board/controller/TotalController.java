package com.notice.board.controller;

import antlr.StringUtils;
import com.notice.board.entity.Bookmark;
import com.notice.board.entity.Liked;
import com.notice.board.entity.Total;
import com.notice.board.security.PrincipalDetail;
import com.notice.board.service.BookMarkService;
import com.notice.board.service.LikeService;
import com.notice.board.service.TotalBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/total")
public class TotalController {

    TotalBoardService service;
    LikeService likeService;
    BookMarkService bookMarkService;

    @Autowired
    public TotalController(TotalBoardService service, LikeService likeService,BookMarkService bookMarkService) {         // controller와 service 연결하는 느낌
        this.service = service;
        this.likeService = likeService;
        this.bookMarkService = bookMarkService;
    }


    @GetMapping("/new")
    public String create() {

        return "/total/Totalcreate";
    }

    // URL 이 변경되지 않은 상태에서 실행
    @PostMapping("/new")           // 게시글 작성
    public String createData(Total total, MultipartFile file) throws IOException {

        // 시큐리티 유저 데이터 받아오는 방식  json으로 변경후 토큰을 같이 보내줘야함
//             Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             PrincipalDetail principalDetail = (PrincipalDetail)principal;
//             String username = principalDetail.getUsername();
//
//             total.setUsername(username);

        service.totalwrite(total, file);
        System.out.println("sdsdssds");
        return "redirect:/ ";    // 제일 첫 페이지로 돌아감
    }

    @GetMapping("/findall")                // 게시글 전체 출력
    public String findAll(@RequestParam(value = "where", required = false)@Nullable String searchlocation, @RequestParam(value = "what",required = false)@Nullable String searchtag,
                          Model model, @PageableDefault(page = 0, size = 4, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable, Total total) {

        if(searchtag == null && searchlocation == null) {           //  맨 처음 화면(검색하기 전)
            // 게시글 통합 출력
            Page<Total> list = service.totallist(pageable);

            // 맛집 출력
            Page<Total> storelist = service.selectAllSQL("맛집",pageable);

            // 숙소 출력
            Page<Total> lodginglist = service.selectAllSQL("숙소",pageable);

            // 명소 출력
            Page<Total> placelist = service.selectAllSQL("명소",pageable);


            int nowpage = list.getPageable().getPageNumber() + 1;    // 게시물 아래의 페이지 번호 구현을 위한 설정 - 현재 페이지
            int startpage = Math.max(nowpage - 4, 1);                 // 현재 페이지 기준으로 앞쪽으로 4개의 페이지 출력
            // 만약 현재 페이지가 1이면 -3이 출력되므로 최소 1로 설정
            int endpage = Math.min(nowpage + 5, list.getTotalPages()); // 마지막 페이지까지 출력

            model.addAttribute("list", list);
            model.addAttribute("storelist", storelist);
            model.addAttribute("lodginglist", lodginglist);
            model.addAttribute("placelist", placelist);

            model.addAttribute("nowpage", nowpage);      // html에서 출력시키기 위해 model을 사용하여  값 전송
            model.addAttribute("startpage", startpage);
            model.addAttribute("endpage", endpage);
        }


        if(searchtag == "" && searchlocation == "") {           //  아무것도 검색하지 않았을때
            // 게시글 통합 출력
            Page<Total> list = service.totallist(pageable);

            // 맛집 출력
            Page<Total> storelist = service.selectAllSQL("맛집",pageable);

            // 숙소 출력
            Page<Total> lodginglist = service.selectAllSQL("숙소",pageable);

            // 명소 출력
            Page<Total> placelist = service.selectAllSQL("명소",pageable);

        int nowpage = list.getPageable().getPageNumber() + 1;    // 게시물 아래의 페이지 번호 구현을 위한 설정 - 현재 페이지
        int startpage = Math.max(nowpage - 4, 1);                 // 현재 페이지 기준으로 앞쪽으로 4개의 페이지 출력
        // 만약 현재 페이지가 1이면 -3이 출력되므로 최소 1로 설정
        int endpage = Math.min(nowpage + 5, list.getTotalPages()); // 마지막 페이지까지 출력

        model.addAttribute("list", list);
            model.addAttribute("storelist", storelist);
            model.addAttribute("lodginglist", lodginglist);
            model.addAttribute("placelist", placelist);
        model.addAttribute("nowpage", nowpage);      // html에서 출력시키기 위해 model을 사용하여  값 전송
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage", endpage);
        }
        else if(searchtag != null && searchlocation == "" ){            // 태그만 검색했을때
            Page<Total> searchtagList = service.tagsearch(searchtag,pageable);

            Page<Total> searchlodging = service.TagAndCategorySearch(searchtag,"숙소",pageable);

            Page<Total> searchstore = service.TagAndCategorySearch(searchtag,"맛집",pageable);

            Page<Total> searchplace = service.TagAndCategorySearch(searchtag,"명소",pageable);

            int nowpage = searchtagList.getPageable().getPageNumber() + 1;    // 게시물 아래의 페이지 번호 구현을 위한 설정 - 현재 페이지
            int startpage = Math.max(nowpage - 4, 1);                 // 현재 페이지 기준으로 앞쪽으로 4개의 페이지 출력
            // 만약 현재 페이지가 1이면 -3이 출력되므로 최소 1로 설정
            int endpage = Math.min(nowpage + 5, searchtagList.getTotalPages()); // 마지막 페이지까지 출력

            model.addAttribute("list", searchtagList);
            model.addAttribute("lodginglist", searchlodging);
            model.addAttribute("storelist", searchstore);
            model.addAttribute("placelist", searchplace);
            model.addAttribute("nowpage", nowpage);      // html에서 출력시키기 위해 model을 사용하여  값 전송
            model.addAttribute("startpage", startpage);
            model.addAttribute("endpage", endpage); 
        }
        else if(searchlocation != null && searchtag == ""){             // 위치만 검색했을때
            Page<Total> searchlocationList = service.locationsearch(searchlocation,pageable);

            Page<Total> searchlodging = service.LocationAndCategorySearch(searchlocation,"숙소",pageable);

            Page<Total> searchstore = service.LocationAndCategorySearch(searchlocation,"맛집",pageable);

            Page<Total> searchplace = service.LocationAndCategorySearch(searchlocation,"명소",pageable);

            int nowpage = searchlocationList.getPageable().getPageNumber() + 1;    // 게시물 아래의 페이지 번호 구현을 위한 설정 - 현재 페이지
            int startpage = Math.max(nowpage - 4, 1);                 // 현재 페이지 기준으로 앞쪽으로 4개의 페이지 출력
            // 만약 현재 페이지가 1이면 -3이 출력되므로 최소 1로 설정
            int endpage = Math.min(nowpage + 5, searchlocationList.getTotalPages()); // 마지막 페이지까지 출력

            model.addAttribute("list", searchlocationList);
            model.addAttribute("lodginglist", searchlodging);
            model.addAttribute("storelist", searchstore);
            model.addAttribute("placelist", searchplace);
            model.addAttribute("nowpage", nowpage);      // html에서 출력시키기 위해 model을 사용하여  값 전송
            model.addAttribute("startpage", startpage);
            model.addAttribute("endpage", endpage);
        }
        else if(searchlocation != null && searchtag != null){               // 위치, 태그 검색했을때
            Page<Total> searchtotalList = service.totalsearch(searchlocation,searchtag,pageable);

            Page<Total> searchlodging = service.LocationAndTagAndCatgorySearch(searchlocation,searchtag,"숙소",pageable);

            Page<Total> searchstore = service.LocationAndTagAndCatgorySearch(searchlocation,searchtag,"맛집",pageable);

            Page<Total> searchplace = service.LocationAndTagAndCatgorySearch(searchlocation,searchtag,"명소",pageable);

            int nowpage = searchtotalList.getPageable().getPageNumber() + 1;    // 게시물 아래의 페이지 번호 구현을 위한 설정 - 현재 페이지
            int startpage = Math.max(nowpage - 4, 1);                 // 현재 페이지 기준으로 앞쪽으로 4개의 페이지 출력
            // 만약 현재 페이지가 1이면 -3이 출력되므로 최소 1로 설정
            int endpage = Math.min(nowpage + 5, searchtotalList.getTotalPages()); // 마지막 페이지까지 출력

            model.addAttribute("list", searchtotalList);
            model.addAttribute("lodginglist", searchlodging);
            model.addAttribute("storelist", searchstore);
            model.addAttribute("placelist", searchplace);
            model.addAttribute("nowpage", nowpage);      // html에서 출력시키기 위해 model을 사용하여  값 전송
            model.addAttribute("startpage", startpage);
            model.addAttribute("endpage", endpage);
        }
        else{
            System.out.println("fail");
        }
        return "total/Totalfindall";
    }


    @GetMapping("/finddetail/{id}")         // 게시물 클릭시 세부 페이지 이동
    public String findTotaldetail(Model model, @PathVariable(value = "id") int id) {

        // like 갯수 출력
        String result = "true";
        List<Liked> likelist = likeService.findByTotalidAndPoint(id,result);
        model.addAttribute("like",likelist.size());

        // 조회수 출력
        Total viewtemp = service.totaldetail(id);
        int temp = viewtemp.getViewcount();
        temp++;
        viewtemp.setViewcount(temp);
        service.save(viewtemp);
        model.addAttribute("view",viewtemp.getViewcount());

        // 게시물 data 출력
        model.addAttribute("data", service.totaldetail(id));

        return "total/Totalfinddetail";
    }

    @GetMapping("/like/{id}")         // 게시물 좋아요
    public String like(Model model, @PathVariable(value = "id") int id, Liked liked) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PrincipalDetail principalDetail = (PrincipalDetail)principal;
        String username = principalDetail.getUsername();

        String name = username;            // 세션 연결전 아이디 디폴트값
        boolean result = likeService.check(id,name);     // db에 데이터가 있는지 유무 확인

        if(result != false){
            liked.setTotalid(id);
            liked.setUsername(name);
            Liked datalist = likeService.like(id,name);       // where totalid and username을 통한 값 저장
            if(datalist.getPoint().equals("false")){
                Liked temp = likeService.likefindByTotalid(id,name);
                liked.setPoint("true");
                temp.setPoint(liked.getPoint());
                likeService.likesave(temp);
            }else if(datalist.getPoint().equals("true")){
                Liked temp = likeService.likefindByTotalid(id,name);
                liked.setPoint("false");
                temp.setPoint(liked.getPoint());
                likeService.likesave(temp);
            }
        }else{
            liked.setTotalid(id);
            liked.setUsername(name);
            liked.setPoint("true");
            likeService.likesave(liked);
        }
        return "redirect:/total/finddetail/{id}";
    }

    @GetMapping("/bookmark/{id}")         // 게시물 북마크
    public String bookmark(Model model, @PathVariable(value = "id") int id, Bookmark bookmark) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PrincipalDetail principalDetail = (PrincipalDetail)principal;
        String username = principalDetail.getUsername();

        String name = username;            // 세션 연결전 아이디 디폴트값
        boolean result = bookMarkService.check(id,name);     // db에 데이터가 있는지 유무 확인

        if(result != false){
            bookmark.setTotalid(id);
            bookmark.setUsername(name);
            Bookmark datalist = bookMarkService.bookmark(id,name);       // where totalid and username을 통한 값 저장
            if(datalist.getBookmark().equals("false")){
                Bookmark temp = bookMarkService.bookmarkfindByTotalid(id,name);
                bookmark.setBookmark("true");
                temp.setBookmark(bookmark.getBookmark());
                bookMarkService.bookmarksave(temp);
            }else if(datalist.getBookmark().equals("true")){
                Bookmark temp = bookMarkService.bookmarkfindByTotalid(id,name);
                bookmark.setBookmark("false");
                temp.setBookmark(bookmark.getBookmark());
                bookMarkService.bookmarksave(temp);
            }
        }else{
            bookmark.setTotalid(id);
            bookmark.setUsername(name);
            bookmark.setBookmark("true");
            bookMarkService.bookmarksave(bookmark);
        }
        return "redirect:/total/finddetail/{id}";
    }

    @GetMapping("/delete")         // 게시물 삭제
    public String Totaldelete(int id) {
        service.totaldelete(id);
        return "redirect:/total/findall";
    }

    @GetMapping("/modify/{id}")            // 게시물 수정을 위한 수정 form
    public String Totalmodify(@PathVariable("id") int id, Model model) {
        model.addAttribute("data", service.totaleditdetail(id));
        return "total/Totalmodify";
    }


    @PostMapping("/update/{id}")        // 게시물 수정
    public String Totalupdate(@PathVariable("id") int id, Total total, MultipartFile file) throws IOException {
        Total temp = service.totaleditdetail(id);

System.out.println(temp);
        temp.setTitle(total.getTitle());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        temp.setLocation(total.getLocation());
        temp.setMemo(total.getMemo());
        temp.setDate(total.getDate());
        temp.setWeather(total.getWeather());
        temp.setMenu(total.getMenu());
        temp.setPrice(total.getPrice());
        temp.setScore(total.getScore());
        temp.setTagList(total.getTagList());
        temp.setCategory(total.getCategory());


        /*
        totaltemp.setTotalname(total.getTotalname());       // 기존의 내용중 이름을 새로운 값으로 덮어씌움
        totaltemp.setTotallocation(total.getTotallocation());
        totaltemp.setTotalmenu(total.getTotalmenu());
        totaltemp.setTotalprice(total.getTotalprice());
        totaltemp.setTotalscore(total.getTotalscore());
        totaltemp.setTotaltag(total.getTotaltag());
        totaltemp.setTotalsubject(total.getTotalsubject());
        */
        service.totalwrite(temp,file);
        return "redirect:/total/findall";
    }

}
