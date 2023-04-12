package service.news;

import config.Config;
import config.Constant;
import model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsServiceIMPL implements INewsService{
    
    List<News> listNews= new Config<News>().readFromFile(Constant.PATH_NEWS);
    @Override
    public List<News> findAll() {
        return listNews;
    }

    @Override
    public void save(News news) {
    News news1= findById(news.getNewsId());
        if (news1 == null) {
            listNews.add(news);
        }else {
            listNews.set(listNews.indexOf(news1),news);
        }
        new Config<News>().writeToFile(Constant.PATH_NEWS, listNews);
    }

    @Override
    public News findById(int id) {
        for (News news:listNews) {
            if (news.getNewsId() == id) {
                return news;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
      News newsDelete= findById(id);
      int index = listNews.indexOf(newsDelete);
      listNews.remove(index);
      new Config<News>().writeToFile(Constant.PATH_NEWS, listNews);
    }


    @Override
    public List<News> searchByName(String name) {
        List<News> listNewsSearch= new ArrayList<>();
        for (News news:listNews) {
            if (news.getName().toLowerCase().contains(name.toLowerCase())) {
                listNewsSearch.add(news);
            }
        }
        return listNewsSearch;
    }

    @Override
    public List<News> searchByCategory(String category) {
        List<News> listSearch = new ArrayList<>();
        for (News news:listNews) {
            if (news.getName().toLowerCase().contains(category.toLowerCase())) {
                listSearch.add(news);
            }
        }
        return listSearch;
    }

    @Override
    public boolean existedByName(String name) {
        for (News news:listNews) {
            if (news.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
 