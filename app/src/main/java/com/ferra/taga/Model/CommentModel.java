package com.ferra.taga.Model;

import android.widget.RatingBar;

import java.util.Map;

public class CommentModel {
    private Float ratingBarValue ;
    private String comment, name, Uid;
    private Map<String, Object> commentTimeStamp;

    public Float getRatingBarValue() {
        return ratingBarValue;
    }

    public void setRatingBarValue(Float ratingBarValue) {
        this.ratingBarValue = ratingBarValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Map<String, Object> getCommentTimeStamp() {
        return commentTimeStamp;
    }

    public void setCommentTimeStamp(Map<String, Object> commentTimeStamp) {
        this.commentTimeStamp = commentTimeStamp;
    }
}
