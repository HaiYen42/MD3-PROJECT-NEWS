package view.user;

import config.Config;
import config.Constant;
import controller.CommentController;
import controller.NewsController;
import model.Comment;
import model.News;
import model.User;

import java.util.List;

public class CommentView {
    CommentController commentController = new CommentController();
    NewsController newsController = new NewsController();
    List<Comment> commentList = commentController.getCommentList();

    public void writeComment(News news, User user) {
        List<Comment> comments = news.getCommentList();
        System.out.println("Enter your comment: ");
        String content = Config.validateString();
        int id;
        if (commentList.size() == 0) {
            id = 1;
        } else {
            id = commentList.get(commentList.size() - 1).getCommentId() + 1;
        }
        Comment newComment = new Comment(id,user,content);
        comments.add(newComment);
        news.setCommentList(comments);
        commentController.createComment(newComment);
        newsController.updateNews(news);
        System.out.println(Constant.CREATE_SUCCESS);
    }
}
