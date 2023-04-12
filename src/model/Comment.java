package model;

import java.io.Serializable;

public class Comment implements Serializable {
    private int commentId;
    private User owner;
    private String content;
    public Comment(){
    }

    public Comment(int commentId, User owner, String content) {
        this.commentId = commentId;
        this.owner = owner;
        this.content = content;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "User: " + owner.getName() + " - Comment: " + content;
    }
}
