package com.crossbridgericenoodle.partycenter.model;

/**
 * 评论模型
 */

public class Comment {


    private String userName;
    private String content;

    public Comment(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}
