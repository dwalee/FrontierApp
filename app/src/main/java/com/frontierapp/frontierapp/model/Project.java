package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.ServerTimestamp;


import java.util.Date;

public class Project {
    private String project_id;
    private String project_name;
    private String project_desc;
    private Date deadline;
    private Date created;
    private Date updated;
    private String created_by, created_by_id;

    public Project() {
    }

    public Project(String project_id, String project_name, String project_desc,
                   Date deadline, Date created, Date updated) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_desc = project_desc;
        this.deadline = deadline;
        this.created = created;
        this.updated = updated;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_desc() {
        return project_desc;
    }

    public void setProject_desc(String project_desc) {
        this.project_desc = project_desc;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @ServerTimestamp
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @ServerTimestamp
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_by_id() {
        return created_by_id;
    }

    public void setCreated_by_id(String created_by_id) {
        this.created_by_id = created_by_id;
    }
}
