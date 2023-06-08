package com.example.project220704.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Column;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "post")
public class Entity_Post extends OrmObject implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Column(name = "id")
    private Integer id;

    @Column(name = "poster")
    private String poster;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "tab")
    private String tab;

    @Column(name = "Anonymous")
    private boolean Anonymous;

    @Column(name = "image")
    private String image;

    @Column(name = "time")
    private Date time;

    @Column(name = "view")
    private int view;

    @Column(name = "commentNum")
    private int commentNum;

    @Column(name = "followNum")
    private int followNum;


    public Entity_Post() {
    }

    public Entity_Post(String poster, String title, String body, String tab, boolean anonymous, String image, Date time, int view, int commentNum, int followNum) {
        this.poster = poster;
        this.title = title;
        this.body = body;
        this.tab = tab;
        Anonymous = anonymous;
        this.image = image;
        this.time = time;
        this.view = view;
        this.commentNum = commentNum;
        this.followNum = followNum;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public boolean isAnonymous() {
        return Anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        Anonymous = anonymous;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
