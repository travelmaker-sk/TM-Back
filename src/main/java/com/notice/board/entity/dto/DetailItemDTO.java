package com.notice.board.entity.dto;

import com.notice.board.entity.Total;
import lombok.Data;

import java.util.List;

@Data
public class DetailItemDTO {


//    private int id;


    private String category;
    private String title;
    private String location;
    private String date;
    private int score;
    private String weather;
    private String menu;
    private String memo;
    private List<String> tagList;
    private int price;
    private String image;

    //DTO to Entity
    public Total toEntity(){
        Total entity = new Total();

        entity.setPrice(this.price);
        entity.setCategory(this.category);
        entity.setDate(this.date);
        entity.setTitle(this.title);
        entity.setLocation(this.location);
        entity.setScore(String.valueOf(this.score));
        entity.setDate(this.date);
        entity.setWeather(this.weather);
        entity.setMemo(this.memo);
        entity.setMenu(this.menu);
        entity.setTagList(tagListArraytoString());
        entity.setImageUrl(this.image);

        return entity;
    }
//    Entity to DTO
    public void toDTO(Total entity){
        this.category = entity.getCategory();
        this.title = entity.getTitle();
        this.location = entity.getLocation();
        this.date = entity.getDate();
        this.score = Integer.parseInt(entity.getScore());
        this.weather = entity.getWeather();
        this.menu = entity.getMenu();
        this.memo = entity.getMemo();
        this.tagList = tagListstringtoArray(entity);
        this.image = entity.getImageUrl();
        this.price = entity.getPrice();
    }

    // 문자열 -> 배열  ... DB에서 문자열 값 뽑고 배열에 넣어줌
    public List<String> tagListstringtoArray(Total entity){
        String result = entity.getTagList();
        return this.tagList = List.of(result.split(" "));

    }

    public String tagListArraytoString(){
        String result = String.join(" ",this.tagList);
        return result;
    }


    public int tagListSize() {
        return tagList.size();
    }

}
