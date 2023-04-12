package service.news;

import model.News;
import service.IGenericService;

import java.util.List;

public interface INewsService extends IGenericService <News>{
    List<News> searchByName(String name);
    List<News> searchByCategory(String category);
    boolean existedByName(String name);
}
