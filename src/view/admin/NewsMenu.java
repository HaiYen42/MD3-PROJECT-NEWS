package view.admin;

import config.Config;

public class NewsMenu {
    public NewsMenu(){
        boolean flag = true;
        while (flag) {
            System.out.println("1. Show List News ");
            System.out.println("2. Create News ");
            System.out.println("3. Update News ");
            System.out.println("4. Details News ");
            System.out.println("5. Delete News ");
            System.out.println("6. Back Menu ");
            System.out.println("Enter your choice ");
            int choice = Config.validateInt();
            switch (choice) {
                case 1:
                    NewsView.getNewsViewInstance().showListNews();
                    break;
                case 2:
                    NewsView.getNewsViewInstance().createNews();
                    break;
                case 3:
                    NewsView.getNewsViewInstance().editNews();
                    break;
                case 4:
                    NewsView.getNewsViewInstance().showDetailNews();
                    break;
                case 5:
                    NewsView.getNewsViewInstance().deleteNews();
                    break;
                case 6:
                    flag = false;
                    break;
            }
        }
    }
}
