package service.comment;

import config.Config;
import config.Constant;
import model.Comment;

import java.util.List;

public class CommentServiceIMPL implements ICommentService{
    List<Comment> commentList = new Config<Comment>().readFromFile(Constant.PATH_COMMENT);
    @Override
    public List<Comment> findAll() {
        return commentList;
    }

    @Override
    public void save(Comment comment) {
        Comment comment1 = findById(comment.getCommentId());
        if (comment1 == null) {
            commentList.add(comment);
        }else {
            commentList.set(commentList.indexOf(comment1),comment);
        }
        new Config<Comment>().writeToFile(Constant.PATH_COMMENT, commentList);
    }

    @Override
    public Comment findById(int id) {
        for (Comment comment:commentList) {
            if (comment.getCommentId() == id) {
                return comment;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        Comment commentDelete = findById(id);
        int index = commentList.indexOf(commentDelete);
        commentList.remove(index);
        new Config<Comment>().writeToFile(Constant.PATH_COMMENT, commentList);
    }
}
