package com.google.assigner_mobile.models;

import android.annotation.SuppressLint;
import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.google.assigner_mobile.adapters.AssignmentsAdapter;
import com.google.assigner_mobile.functions.DateFunction;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Vector;

public class Assignment {

    private int id;
    private Integer groupId;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate deadline;

    public Assignment(int id, int groupId, String name, String description, LocalDate createdAt, LocalDate deadline) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
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

    @SuppressLint("DefaultLocale")
    public String calculateDeadlineDueAsText(LocalDate deadline) {

        long dueInDays = new DateFunction().calculateDateDiffFromNow(deadline);

        String deadlineTimeLeft;

        if(dueInDays <= 0)
            deadlineTimeLeft = "Overdue";
        else if(dueInDays < 30)
            deadlineTimeLeft = String.format("Due in %d days", dueInDays);
        else if(dueInDays < 365)
            deadlineTimeLeft = String.format("Due in %d months and %d days", dueInDays / 30, dueInDays % 30);
        else
            deadlineTimeLeft = String.format("Due in %d years", dueInDays / 365);

        return deadlineTimeLeft;
    }

    public int getDueColor(LocalDate deadline) {

        long dueInDays = new DateFunction().calculateDateDiffFromNow(deadline);

        if(dueInDays <= 0)
            return Color.parseColor("#DC3545");
        else if(dueInDays < 30)
            return Color.parseColor("#FFC107");
        else
            return Color.parseColor("#198754");

    }

}
