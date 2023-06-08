package com.example.project220704.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Column;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "follow")
public class Entity_Follow extends OrmObject implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Column(name = "id")
    private Integer id;

    @Column(name = "usrName")
    private String usrName;

    @Column(name = "postId")
    private Integer postId;

    @Column(name = "followTime")
    private Date followTime;

    public Entity_Follow() {
    }

    public Entity_Follow(String usrName, Integer postId, Date followTime) {
        this.usrName = usrName;
        this.postId = postId;
        this.followTime = followTime;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
