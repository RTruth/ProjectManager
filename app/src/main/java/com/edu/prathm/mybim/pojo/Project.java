package com.edu.prathm.mybim.pojo;

import java.util.Date;

/**
 * Created by Prathm on 8/11/2015.
 */
public class Project {
    String project_name;
    String project_owner;
    String p_id;
    String team_id;
    Date start_date;
   Date end_date;
    String u_id;
    public String getP_id() {
        return p_id;
    }
    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }
    public String getU_id(){return u_id; }
    public void setU_id(String u_id)
    {this.u_id=u_id;}

    public void setStart_date(Date start_date) {
        this.start_date =start_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date =end_date;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_owner() {
        return project_owner;
    }

    public void setProject_owner(String project_owner) {
        this.project_owner = project_owner;
    }
}
