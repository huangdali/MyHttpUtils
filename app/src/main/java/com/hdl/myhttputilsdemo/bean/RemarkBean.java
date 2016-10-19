package com.hdl.myhttputilsdemo.bean;

/**
 * Created by HDL on 2016/10/18.
 */

import java.io.Serializable;

/**
 * Created by HDL on 2016/9/1.
 */
public class RemarkBean implements Serializable{

    /**
     * comments : 算了的高考
     * message : 检索成功
     * status : SUCCESS
     * tracks : panzhenxing
     */

    private String comments;
    private String message;
    private String status;
    private String tracks;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "RemarkBean{" +
                "comments='" + comments + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", tracks='" + tracks + '\'' +
                '}';
    }
}