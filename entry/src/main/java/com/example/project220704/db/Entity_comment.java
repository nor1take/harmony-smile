package com.example.project220704.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Column;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "comment")
public class Entity_comment extends OrmObject implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Column(name = "id")
    private Integer id;

    @Column(name = "postId")
    private Integer postId;

    @Column(name = "commentId")
    private Integer commentId;

    @Column(name = "usrName")
    private String usrName;

    @Column(name = "commentBody")
    private String commentBody;

    @Column(name = "commentTime")
    private Date commentTime;

    @Column(name = "likeNum")
    private Integer likeNum;

    @Column(name = "commentNum")
    private Integer commentNum;

    @Column(name = "isLiker")
    private boolean isLiker;

    public Entity_comment() {
    }

    public Entity_comment(Integer postId, Integer commentId, String usrName, String commentBody, Date commentTime, Integer likeNum, Integer commentNum, boolean isLiker) {
        this.postId = postId;
        this.commentId = commentId;
        this.usrName = usrName;
        this.commentBody = commentBody;
        this.commentTime = commentTime;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.isLiker = isLiker;
    }


    public boolean isIsLiker() {
        return isLiker;
    }

    public void setIsLiker(boolean liker) {
        isLiker = liker;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
