package com.changgou.comment.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @Program: changgou_parent
 * @ClassName: CommentInfo
 * @Description: 商品详细页面评价展示实体类
 * @Author: KyleSun
 **/
@Document(collection = "tb_comment_info")
public class CommentInfo implements Serializable {

    private String username;        //用户名

    private String content;         //评论内容

    private String level;           //评论等级

    private String userPhoto;       //用户头像

    private List<String> specList;  //规格信息，只取相关的value

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public List<String> getSpecList() {
        return specList;
    }

    public void setSpecList(List<String> specList) {
        this.specList = specList;
    }
}
