package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class News implements Serializable {
    private int newsId;
    private String name;
    private String content;

    private Category category;
    private LocalDate date = LocalDate.now();
    private List<User> userListLike = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();

    public List<User> getUserListLike() {
        return userListLike;
    }

    public void setUserListLike(List<User> userListLike) {
        this.userListLike = userListLike;
    }

    public News() {
    }

    public News(int newsId, String name, String content, Category category, LocalDate date) {
        this.newsId = newsId;
        this.name = name;
        this.content = content;
        this.category = category;
        this.date = date;
    }

    public News(int id, String name, String content, Category category) {
        this.newsId = id;
        this.name = name;
        this.content = content;
        this.category = category;

    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category +
                ", date=" + date +
                '}';
    }
}
