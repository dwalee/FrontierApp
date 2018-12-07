package com.frontierapp.frontierapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    String task_id;
    String task_name;
    String task_desc;
    Date created, updated, deadline;
    List<String> assignees;
    String status;
    String priority;

    public Task() {
    }

    public Task(String task_id, String task_name, String task_desc, Date created, Date updated,
                Date deadline, List<String> assignees, String status, String priority) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.created = created;
        this.updated = updated;
        this.deadline = deadline;
        this.assignees = assignees;
        this.status = status;
        this.priority = priority;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<String> assignees) {
        this.assignees = assignees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public enum TaskStatus{
        OPEN("OPEN"),
        IN_PROGRESS("IN PROGRESS"),
        COMPLETED("COMPLETED"),
        CLOSED("CLOSED");

        TaskStatus(String value){this.value = value;}

        private String value;

        public String getValue(){ return value;}
    }

    public enum Priority{
        URGENT("Urgent"),
        HIGH("High"),
        NORMAL("Normal"),
        LOW("Low");

        Priority(String value){this.value = value;}

        private String value;

        public String getValue(){ return value;}
    }
}
