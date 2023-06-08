package com.example.project220704.db;

public class Entity_Help_Item {
    String title;
    String body;

    public Entity_Help_Item() {
    }

    public Entity_Help_Item(String title, String body) {
        this.title = title;
        this.body = body;
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
}
