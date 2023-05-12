package com.google.assigner_mobile.models;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.google.assigner_mobile.functions.DateFunction;
import java.time.LocalDate;

public class Assignment {

    private int id;
    private Integer userId;
    private Integer groupId;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate deadline;
    private Integer progress;


    public Assignment(int id, int userId, int groupId, String name, String description, LocalDate createdAt, LocalDate deadline) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.progress = 0;
    }

    public Assignment(int id, int userId, int groupId, String name, String description, LocalDate createdAt, LocalDate deadline, Integer progress) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }

    /**
     * Fungsi untuk menghitung deadline assignment dan mengembalikannya dalam bentuk teks (String)
     * @param deadline tanggal deadline assignment
     * @return String teks deadline assignment
     */
    @SuppressLint("DefaultLocale")
    public String calculateDeadlineDueAsText(LocalDate deadline) {

        long dueInDays = new DateFunction().calculateDateDiffFromNow(deadline);

        String deadlineTimeLeftText;

        if(dueInDays <= 0)
            deadlineTimeLeftText = "Overdue";
        else if(dueInDays < 30)
            deadlineTimeLeftText = String.format("Due in %d days", dueInDays);
        else if(dueInDays < 365)
            deadlineTimeLeftText = String.format("Due in %d months and %d days", dueInDays / 30, dueInDays % 30);
        else
            deadlineTimeLeftText = String.format("Due in %d years", dueInDays / 365);

        return deadlineTimeLeftText;
    }

    /**
     * Fungsi untuk menghitung deadline assignment dan mengembalikannya dalam bentuk warna (int)
     * @param deadline
     * @return int warna deadline assignment
     */
    public int getDueColor(LocalDate deadline) {

        long dueInDays = new DateFunction().calculateDateDiffFromNow(deadline);

        if(dueInDays <= 0)
            return Color.parseColor("#DC3545"); // Red
        else if(dueInDays < 3)
            return Color.parseColor("#FFC107"); // Yellow
        else
            return Color.parseColor("#198754"); // Green

    }

}
