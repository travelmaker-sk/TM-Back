package com.notice.board.entity.dto;

import com.notice.board.entity.Total;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DetailResponse {

    private int viewCount;
    private int id;
    private String category;
    private String title;
    private String location;
    private String date;
    private int score;
    private String weather;
    private String menu;
    private int price;
    private String memo;
    private List<String> tagList;
    private String  image; //???
    private ArrayList<LikeDTO> like;
    private boolean bookmarkCheck;
    private ArrayList<WriterDTO> writer;
    private LocalDate createDate;// 작성자가 여행한 날짜


    public void toDTO(Total totalEntity){
        this.category = totalEntity.getCategory();
        this.title = totalEntity.getTitle();
        this.location = totalEntity.getLocation();
        this.date = totalEntity.getDate();
        this.score = Integer.parseInt(totalEntity.getScore());
        this.weather = totalEntity.getWeather();
        this.menu = totalEntity.getMenu();
        this.memo = totalEntity.getMemo();
        this.tagList = tagListstringtoArray(totalEntity);
        this.createDate = totalEntity.getCreatedate();
        this.price = totalEntity.getPrice();
        this.image = totalEntity.getImageUrl();
        this.viewCount = totalEntity.getViewcount();


    }

    public List<String> tagListstringtoArray(Total entity){
        String result = entity.getTagList();
        return this.tagList = List.of(result.split(" "));

    }

    public String tagListArraytoString(){
        String result = String.join(" ",this.tagList);
        return result;
    }


    //Liked라는 Entity가 있어요
    @Data
    public static class LikeDTO{
        private int likeNum;
        private boolean likeCheck;

        public LikeDTO(int likeNum, boolean likeCheck){
            this.likeNum = likeNum;
            this.likeCheck = likeCheck;

//        public LikeDTO(Liked LikedEntity){
//            this.likeCheck = Boolean.parseBoolean(LikedEntity.getPoint()); //************************수정
//            this.likeNum = LikedEntity.getPoint(); //**************수정
        }
    }


    // User정보를 다른 Entity에서 끌어와서 쓸거임
    @Data
    public static class WriterDTO{
        private String username;
        private String profileimage;
    // User정보 받아올 떄 작성 ******************************8

        public WriterDTO(String username,String profileimage){
            this.username = username;
            this.profileimage = profileimage;
        }
    }
}
