package controller;

import config.Constant;
import dto.response.ResponseMessage;
import model.News;
import model.User;
import service.news.INewsService;
import service.news.NewsServiceIMPL;

import java.util.List;

public class NewsController {
    INewsService newsService = new NewsServiceIMPL();

    public List<News> getNewsList() {
        return newsService.findAll();
    }

    public ResponseMessage createNews(News news) {
        if (newsService.existedByName(news.getName())) {
            return new ResponseMessage(Constant.NEWS_EXISTED);
        } else {
            newsService.save(news);
            return new ResponseMessage(Constant.CREATE_SUCCESS);
        }
    }

    public void updateNews(News news) {
        newsService.save(news);
    }

    public News findNewsById(int id) {
        return newsService.findById(id);
    }

    public List<News> findNewsByName(String name) {
        return newsService.searchByName(name);
    }

    public List<News> findNewsByCategory(String category) {
        return newsService.searchByCategory(category);
    }

    public void deleteNewsById(int id) {
        newsService.deleteById(id);
    }

    public boolean likeNews(News news, User user) {
        // B1: Lấy ra danh sách những người đã like bài báo này:
        List<User> userListLike = news.getUserListLike();
        // B2: Tìm trong danh sách này có tồn tại người đang đăng nhập hay không:
        for (User u : userListLike) {
            //B3: Nếu tồn tại thì --> xóa khỏi danh sách những người đã like
            if (u.getId() == user.getId()) {
                userListLike.remove(u);
                news.setUserListLike(userListLike);
                newsService.save(news);
                return false;
            }
        }
        //B4: Nếu không tồn tại thì thêm vào danh sách
        userListLike.add(user);
        news.setUserListLike(userListLike);
        newsService.save(news);
        return true;
    }
    public boolean checkLike(News news, User user){
        List<User> userListLike = news.getUserListLike();
        for (User u : userListLike) {
            if (u.getId() == user.getId()) {
                return true;
            }
        }
        return false;
    }
}
