package view.admin;

import config.Config;
import config.Constant;
import controller.CategoryController;
import controller.NewsController;
import dto.response.ResponseMessage;
import model.Category;
import model.Comment;
import model.News;
import model.User;
import view.user.CommentView;

import java.util.List;
import java.util.Scanner;

public class NewsView {
    public static NewsView newsViewInstance;

    public static NewsView getNewsViewInstance() {
        if (newsViewInstance == null) newsViewInstance = new NewsView();
        return newsViewInstance;
    }

    NewsController newsController = new NewsController();
    List<News> newsList = newsController.getNewsList();
    CategoryController categoryController = new CategoryController();
    CommentView commentView = new CommentView();
    List<Category> categoryList = categoryController.getCategoryList();
    List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);

    public void showListNews() {
        while (true) {
            System.out.println("News List ");
            for (int i = 0; i < newsList.size(); i++) {
                System.out.println((i + 1) + ". " + newsList.get(i).getName() + " - Category: " + newsList.get(i).getCategory().getName());
            }
            System.out.println("Enter back to comeback to menu ");
            String backMenu = Config.scanner().nextLine();
            if (backMenu.equalsIgnoreCase("back")) {
                new NewsView();
                break;
            }
        }
    }

    public void createNews() {
        int id;
        if (newsList.size() == 0) {
            id = 1;
        } else {
            id = newsList.get(newsList.size() - 1).getNewsId() + 1;
        }
        System.out.println(" Enter the News name ");
        String name = Config.scanner().nextLine();
        System.out.println("Enter the News content ");
        String content = Config.scanner().nextLine();
        System.out.println("Category List is: ");
        for (Category category : categoryList) {
            System.out.printf("Id: %d- Name: %s \n", category.getId(), category.getName());
        }
        System.out.println("Enter the News Category id ");
        int idCategory = Config.validateInt();
        for (int i = 0; i < categoryList.size(); i++) {
            if (idCategory == i) {
                System.out.println(categoryList.get(i).getName());
            }
        }
        News news = new News(id, name, content, categoryController.findCategoryById(idCategory));
        ResponseMessage responseMessage = newsController.createNews(news);
        if (responseMessage.getMessage().equals(Constant.NEWS_EXISTED)) {
            System.out.println(Constant.NEWS_EXISTED);
        } else {
            System.out.println(Constant.CREATE_SUCCESS);
        }
    }

    public void editNews() {
        System.out.println("Edit News ");
        System.out.println("News List ");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println("ID: " + newsList.get(i).getNewsId() + ". " + newsList.get(i).getName() + " - Category: " + newsList.get(i).getCategory().getName());
        }
        while (true) {
            System.out.println("Enter the Id of News you want to Edit ");
            int id = Config.validateInt();
            News news = newsController.findNewsById(id);
            if (news != null) {
                System.out.println("Enter the new name of News ");
                news.setName(Config.scanner().nextLine());
                System.out.println("Enter the new content of News ");
                news.setContent(Config.scanner().nextLine());
                for (Category category : categoryList) {
                    System.out.printf("Id: %d- Name: %s \n", category.getId(), category.getName());
                }
                System.out.println("Enter the Category Id you want to edit ");
                int idCategory = Config.validateInt();
                Category categoryEdit = categoryController.findCategoryById(idCategory);
                if (categoryEdit != null) {
                    news.setCategory(categoryEdit);
                } else {
                    System.out.println(Constant.ID_NOT_EXISTED);
                }
                newsController.updateNews(news);
                System.out.println(Constant.EDIT_SUCCESS);
                break;
            }
            System.out.println(Constant.ID_NOT_EXISTED);
        }
    }

    public void showDetailNews() {
        System.out.println("New List ");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println("ID: " + newsList.get(i).getNewsId() + " . " + newsList.get(i).getName() + " " + newsList.get(i).getCategory().getName());
        }
        System.out.println("Enter the ID of News you want to see Details ");
        int id = Config.validateInt();
        News newsDetail = newsController.findNewsById(id);
        if (newsDetail == null) {
            System.out.println(Constant.ID_NOT_EXISTED);
        } else {
            System.out.println("ID " + newsDetail.getNewsId() + " - Name: " + newsDetail.getName() + " - Details: " + newsDetail.getContent() + " -Category: " + newsDetail.getCategory().getName());
        }
        System.out.println("Enter back to comeback to Menu ");
        String backMenu = Config.scanner().nextLine();
        if (backMenu.equalsIgnoreCase("back")) {
            new NewsView();
        }
    }

    public void deleteNews() {
        System.out.println("New List ");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println("ID: " + newsList.get(i).getNewsId() + " . " + newsList.get(i).getName() + " " + newsList.get(i).getCategory().getName());
        }
        while (true) {
            boolean flag = false;
            System.out.println("Enter the ID of news you want to delete ");
            int id = Config.validateInt();
            News newsDelete = newsController.findNewsById(id);
            if (newsDelete != null) {
                while (true) {
                    System.out.println("Are you sure to delete this news Y/N ");
                    String choice = Config.scanner().nextLine();
                    if (choice.equalsIgnoreCase("Y")) {
                        newsController.deleteNewsById(id);
                        System.out.println("Delete successful ! ");
                        flag = true;
                        break;
                    }
                    if (choice.equalsIgnoreCase("N")) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                new NewsView();
                break;
            }
            System.out.println(Constant.ID_NOT_EXISTED);
        }
    }

    public void findNewsByName() {
        System.out.println("Enter the name of News you want to find ");
        String name = Config.scanner().nextLine();
        List<News> listFindNews = newsController.findNewsByName(name);
        News recentNew;
        if (listFindNews.size() > 0) {
            for (News news : listFindNews) {
                System.out.println("ID: " + news.getNewsId() + " Name: " + news.getName() + " - Category: " + news.getCategory());
            }
            int id;
            boolean check = true;
            boolean checkLike;
            do {
                System.out.println("Enter a news's ID that you want to read: ");
                id = Config.validateInt();
                for (News listFindNew : listFindNews) {
                    if (listFindNew.getNewsId() == id) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    System.out.println(Constant.ID_NOT_EXISTED);
                }
            } while (check);
            recentNew = newsController.findNewsById(id);
            readNews(recentNew);
            checkLike = newsController.checkLike(recentNew, userLogin.get(0));
            System.out.println((checkLike ? "1. Unlike" : "1. Like") + " - 2. Comment " + " - 3. Back Menu");
            int choice;
            do {
                System.out.println("Enter your choice: ");
                choice = Config.validateInt();
            } while (choice < 1 || choice > 3);
            if (choice == 1) {
                boolean notice = newsController.likeNews(recentNew, userLogin.get(0));
                if (notice) {
                    System.out.println("Like successful!");
                } else {
                    System.out.println("Unlike successful!");
                }
                NewsView.getNewsViewInstance();
            }
            if (choice == 2) {
                commentView.writeComment(recentNew, userLogin.get(0));
                NewsView.getNewsViewInstance();
            }
            if (choice == 3) {
                NewsView.getNewsViewInstance();
            }
        } else {
            System.out.println("Not found this news ");
            NewsView.getNewsViewInstance();
        }


    }

    public void findNewsByCategory() {
        System.out.println("Enter the Category of News you want to find ");
        String category = Config.scanner().nextLine();
        List<News> listSearch = newsController.findNewsByCategory(category);
        News recentNew;
        if (listSearch.size() >0) {
            for (News news: listSearch) {
                System.out.println("ID: " + news.getNewsId() + " Name: " + news.getName() + " - Category: " + news.getCategory());
            }
            int id;
            boolean check = true;
            boolean checkLike;
            do {
                System.out.println("Enter a news's ID that you want to read: ");
                id = Config.validateInt();
                for (News listFindNew : listSearch) {
                    if (listFindNew.getNewsId() == id) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    System.out.println(Constant.ID_NOT_EXISTED);
                }
            } while (check);
            recentNew = newsController.findNewsById(id);
            readNews(newsController.findNewsById(id));
            checkLike = newsController.checkLike(recentNew, userLogin.get(0));
            System.out.println((checkLike ? "1. Unlike" : "1. Like") + " - 2. Comment " + " - 3. Back Menu");
            int choice;
            do {
                System.out.println("Enter your choice: ");
                choice = Config.validateInt();
            } while (choice < 1 || choice > 3);
            if (choice == 1) {
                boolean notice = newsController.likeNews(recentNew, userLogin.get(0));
                if (notice) {
                    System.out.println("Like successful!");
                } else {
                    System.out.println("Unlike successful!");
                }
                NewsView.getNewsViewInstance();
            }
            if (choice == 2) {
                commentView.writeComment(recentNew, userLogin.get(0));
                NewsView.getNewsViewInstance();
            }
            if (choice == 3) {
                NewsView.getNewsViewInstance();
            }
        } else {
            System.out.println("News not found ");
        }
        System.out.println("Enter back to comeback to Menu ");
        String backMenu = Config.scanner().nextLine();
        if (backMenu.equalsIgnoreCase("back")) {
            new NewsView();
        }
    }

    public void readNews(News news) {
        System.out.println("------------------- READ NEWS -------------------");
        System.out.println("Name: " + news.getName());
        System.out.println("Category: " + news.getCategory());
        System.out.println("-------------------  Content  -------------------");
        System.out.println(news.getContent());
        System.out.println("-------------------------------------------------");
        for (Comment com : news.getCommentList()) {
            System.out.println(com);
        }
        System.out.println("Created Date: " + news.getDate());
    }

}
