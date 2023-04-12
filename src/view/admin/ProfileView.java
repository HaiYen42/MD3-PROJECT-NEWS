package view.admin;

import config.Config;
import controller.UserController;
import model.Role;
import model.RoleName;
import model.User;
import view.user.UserView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileView {
    UserController userController = new UserController();

    public ProfileView() {
        User user = userController.getUserLogin();
        Set<Role> roleSet = user.getRoles();
        while (true) {
            if (user != null) {
                List<Role> roles = new ArrayList<>(roleSet);
                if (roles.get(0).getName() == RoleName.ADMIN) {
                    // ADMIN
                    System.out.println("MENU ADMIN ");
                    System.out.println("1. User Management ");
                    System.out.println("2. Category Management ");
                    System.out.println("3. News Management ");
                    System.out.println("4. Logout ");
                    System.out.println("Enter your choice ");
                    int choice = Config.validateInt();
                    switch (choice) {
                        case 1:
                            new UserManagementView();
                            break;
                        case 2:
                            new CategoryView();
                            break;
                        case 3:
                            new NewsMenu();
                            break;
                        case 4:
                            new UserView().formLogout();
                            break;
                    }
                } else if (roles.get(0).getName() == RoleName.USER) {
                    //User
                    System.out.println("MENU USER ");
                    System.out.println("1. Search News by Name ");
                    System.out.println("2. Search News by Category ");
                    System.out.println("3. Change Password ");
                    System.out.println("4. Logout ");
                    System.out.println("Enter your choice ");
                    int choice = Config.validateInt();
                    switch (choice) {
                        case 1:
                            NewsView.getNewsViewInstance().findNewsByName();
                            break;
                        case 2:
                            NewsView.getNewsViewInstance().findNewsByCategory();
                            break;
                        case 3:
                            UserView.getUserViewInstance().changePassword();
                            break;
                        case 4:
                            new UserView().formLogout();
                            break;
                    }
                } else {
                    //PM
                    System.out.println("MENU PM ");
                    System.out.println("1. User Management ");
                    System.out.println("2. Category Management ");
                    System.out.println("3. News Management ");
                    System.out.println("4. Logout ");
                    System.out.println("Enter your choice ");
                    int choice = Config.validateInt();
                    switch (choice){
                        case 1:
                            new UserManagementView();
                            break;
                        case 2:
                            new CategoryView();
                            break;
                        case 3:
                            new NewsMenu();
                            break;
                        case 4:
                            new UserView().formLogout();
                            break;
                    }
                }
            }
        }
    }
}
