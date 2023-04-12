package view.admin;

import config.Config;
import controller.UserController;
import model.Role;
import model.RoleName;
import model.User;
import view.user.UserView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserManagementView {
    UserController userController = new UserController();
    public UserManagementView(){
        User user = userController.getUserLogin();
        Set<Role> roleSet = user.getRoles();
        while (true){
            if (user != null) {
                List<Role> roles = new ArrayList<>(roleSet);
                if (roles.get(0).getName()== RoleName.ADMIN) {
                    System.out.println("USER MANAGEMENT");
                    System.out.println("1. Change Role ");
                    System.out.println("2. Block User ");
                    System.out.println("3. Delete User ");
                    System.out.println("4. Back ");
                    System.out.println("Enter your choice ");
                    int choice = Config.validateInt();
                    switch (choice){
                        case 1:
                            UserView.getUserViewInstance().changeUserRole();
                            break;
                        case 2:
                            UserView.getUserViewInstance().blockUser();
                            break;
                        case 3:
                            UserView.getUserViewInstance().deleteUserById();
                            break;
                        case 4:
                            new ProfileView();
                            break;
                    }
                }else{
                    System.out.println("1. Block User ");
                    System.out.println("2. Delete User ");
                    System.out.println("3. Back ");
                    System.out.println("Enter your choice ");
                    int choice = Config.validateInt();
                    switch (choice){
                        case 1:
                            UserView.getUserViewInstance().blockUser();
                            break;
                        case 2:
                            UserView.getUserViewInstance().deleteUserById();
                            break;
                        case 3:
                            new ProfileView();
                            break;
                    }
                }
            }
        }
    }
}
