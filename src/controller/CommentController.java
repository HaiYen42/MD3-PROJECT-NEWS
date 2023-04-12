package controller;

import model.Comment;
import service.comment.CommentServiceIMPL;
import service.comment.ICommentService;

import java.util.List;

public class CommentController {
    ICommentService commentService = new CommentServiceIMPL();
    public List<Comment> getCommentList(){
        return commentService.findAll();
    }
    public void createComment(Comment comment){
        commentService.save(comment);
    }
    public Comment findCommentById(int id){
        return commentService.findById(id);
    }
    public void deleteComment(int id){
        commentService.deleteById(id);
    }
}
