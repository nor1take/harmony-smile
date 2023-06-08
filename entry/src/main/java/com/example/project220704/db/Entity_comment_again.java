package com.example.project220704.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Column;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "commentAgain")
public class Entity_comment_again extends OrmObject implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Column(name = "id")
    private Integer id;

    @Column(name = "commentId")
    private Integer commentId;

    @Column(name = "newName")
    private String newName;

    @Column(name = "oldName")
    private String oldName;

    @Column(name = "commentBody")
    private String commentBody;

    @Column(name = "commentTime")
    private Date commentTime;

    public Entity_comment_again(Integer commentId, String newName, String oldName, String commentBody, Date commentTime) {
        this.commentId = commentId;
        this.newName = newName;
        this.oldName = oldName;
        this.commentBody = commentBody;
        this.commentTime = commentTime;
    }

    public Entity_comment_again() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
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
}
