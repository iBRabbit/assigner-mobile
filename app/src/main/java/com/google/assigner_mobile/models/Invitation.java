package com.google.assigner_mobile.models;

public class Invitation {

    private int id;
    private int groupId;
    private int userId;

    public Invitation(int id, int groupId, int userId) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
