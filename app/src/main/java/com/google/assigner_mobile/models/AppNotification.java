package com.google.assigner_mobile.models;

import java.time.LocalDate;

public class AppNotification {

    private int id;
    private int user_id;
    private String title;
    private String message;
    private Integer type;
    private LocalDate createdAt;

    public final static int TYPE_INVITATION = 1;
    public final static int TYPE_ASSIGNMENT = 2;
    public final static int TYPE_REMINDER = 3;

    public AppNotification(int id, int user_id, String title, String message, Integer type, LocalDate createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
