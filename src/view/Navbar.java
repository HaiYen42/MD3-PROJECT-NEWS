package view;

import config.Config;
import controller.UserController;
import model.User;
import view.admin.ProfileView;
import view.user.UserView;

public class Navbar {
    UserController userController = new UserController();
    public Navbar() {
        User user = userController.getUserLogin();
        if (user != null) {
            new ProfileView();
        } else {
            while (true) {
                System.out.println("1. Register  ");
                System.out.println("2. Login ");
                System.out.println("3. Logout ");
                System.out.println("4. Show List User ");
                System.out.println("5. Exit ");
                System.out.println("Enter your choice ");
                int choice = Config.validateInt();
                switch (choice) {
                    case 1:
                        new UserView().register();
                        break;
                    case 2:
                        new UserView().formLogin();
                        break;
                    case 3:
                        new UserView().formLogout();
                        break;
                    case 4:
                        new UserView().showListUser();
                        break;
                    case 5:
                        System.exit(0);
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Navbar();
    }
}
