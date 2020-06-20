package com.changgou.comment.pojo;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Program: changgou_parent
 * @ClassName: Comment
 * @Description:
 * @Author: KyleSun
 **/
@Document(collection = "tb_comment")
public class Comment implements Serializable {

    @Id
    private String id;          //主键id

    private String username;    //用户名

    private String orderId;     //订单id

    private String skuId;       //商品id

    private String spuId;       //商品spuId

    private String content;     //评论内容

    private String level;       //评论等级

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
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
}
