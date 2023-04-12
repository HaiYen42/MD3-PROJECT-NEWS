package view.user;

import config.Config;
import config.Constant;
import controller.UserController;
import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessage;
import model.Role;
import model.RoleName;
import model.User;
import view.Navbar;
import view.admin.UserManagementView;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserView {
    UserController userController = new UserController();
    List<User> listUser = userController.getListUser();
    List<User> currentUserList = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);

    public static UserView userViewInstance;
    public static UserView getUserViewInstance(){
        if (userViewInstance == null) {
            userViewInstance= new UserView();
        }
        return userViewInstance;
    }

    public void register() {
        int id;
        //Tạo id tự tăng
        if (listUser.size() == 0) {
            id = 1;
        } else {
            id = listUser.get(listUser.size() - 1).getId() + 1;
        }
        String name, email, username, password;
        do {
            System.out.println("Enter the name ");
            name = Config.scanner().nextLine();
            if (!Config.validateName(name)) {
                System.err.println(Config.FORMAT_ALERT);
            }
        } while (!Config.validateName(name));
        do {
            System.out.println("Enter the email ");
            email = Config.scanner().nextLine();
            if (!Config.validateEmail(email)) {
                System.err.println(Config.FORMAT_ALERT);
            }
        } while (!Config.validateEmail(email));
        do {
            System.out.println("Enter the username ");
            username = Config.scanner().nextLine();
            if (!Config.validateUsername(username)) {
                System.err.println(Config.FORMAT_ALERT);
            }
        } while (!Config.validateUsername(username));
        do {
            System.out.println("Enter the password ");
            password = Config.scanner().nextLine();
            if (!Config.validatePassword(password)) {
                System.err.println(Config.FORMAT_ALERT + "  Có 1 chữ hoa, Có kí tự số, 1 ký tự đặc biệt, và các ký tự thường, tối đa 10 ký tự.");
            }
        } while (!Config.validatePassword(password));
        Set<String> strRole = new HashSet<>();
        SignUpDTO sign = new SignUpDTO(id, name, username, email, password, strRole);
        while (true) {
            ResponseMessage responseMessage = userController.register(sign);
            System.out.println(responseMessage.getMessage());
            if (responseMessage.getMessage().equals(Constant.USERNAME_EXISTED)) {
                System.err.println("User name existed ! ");
                do {
                    System.out.println("Enter the user name");
                    username = Config.scanner().nextLine();
                    if (!Config.validateUsername(username)) {
                        System.err.println(Config.FORMAT_ALERT + " not be void, less than 30 character, no space between characters !  ");
                    }
                } while (!Config.validateUsername(username));
                sign.setUsername(username);
            } else if (responseMessage.getMessage().equals(Constant.EMAIL_EXISTED)) {
                System.err.println("Email existed ! ");
                do {
                    System.err.println("Enter the email ");
                    email = Config.scanner().nextLine();
                    if (!Config.validateEmail(email)) {
                        System.err.println(Config.FORMAT_ALERT);
                    }
                } while (!Config.validateEmail(email));
                sign.setEmail(email);
            } else {
                System.out.println(Config.SUCCESS_ALERT + " Welcome to " + name);
                formLogin();
                break;
            }
        }
    }

    public void showListUser() {
        System.out.println(listUser);
        System.out.println("Enter back to return Menu ");
        String back = Config.scanner().nextLine();
        if (back.equalsIgnoreCase("back")) ;
        new Navbar();
    }

    public void formLogin() {
        System.out.println("Login Form ");
        System.out.println("Enter your username ");
        String username = Config.scanner().nextLine();
        System.out.println("Enter your password ");
        String password = Config.scanner().nextLine();
        SignInDTO signInDTO = new SignInDTO(username, password);
        while (true) {
            int count = 1;
            ResponseMessage responseMessage = userController.login(signInDTO);
            if (responseMessage.getMessage().equals(Constant.LOGIN_FAILED)) {
                System.out.println("Login failure, please try again ");
                System.out.println("Enter your user name");
                username = Config.scanner().nextLine();
                System.out.println("Enter your password ");
                password = Config.scanner().nextLine();
                signInDTO.setUsername(username);
                signInDTO.setPassword(password);
                count++;
                if (count == 5) {
                    System.out.println(" You login more than 5 times, try again !!!");
                    new Navbar();
                    break;
                }
            } else {
                System.out.println("Login successful ");
                new Navbar();
                break;
            }
        }

    }

    public void formLogout() {
        while (true) {
            System.out.println("Are you sure you want to Logout Y/N ?");
            String choice = Config.scanner().nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                userController.logout();
                new Navbar();
                break;
            }
            if (choice.equalsIgnoreCase("N")) {
                new Navbar();
                break;
            }
        }
    }

    public void displayUserListIgnoreAdmin() {
        for (User user : listUser) {
            Set<Role> roleSet = user.getRoles();
            List<Role> roles = new ArrayList<>(roleSet);
            if (roles.get(0).getName() == RoleName.PM || roles.get(0).getName() == RoleName.USER) {
                System.out.printf("ID: %d- Name: %s -Status: %s\n", user.getId(), user.getName(), user.isStatus());
            }
        }
    }

    public void deleteUserById() {
        Set<Role> roleSet = currentUserList.get(0).getRoles();
        List<Role> roleList = new ArrayList<>(roleSet);
        if (roleList.get(0).getName() == RoleName.ADMIN||roleList.get(0).getName()==RoleName.PM) {
            displayUserListIgnoreAdmin();
            System.out.println("Enter the ID of User you want to delete ");
            int id = Config.validateInt();
            User user = userController.findUserDetailById(id);
            if (user != null && id != 0) {
                System.out.printf("User: %s \n", user.getName());
                System.out.println("Are you sure to delete this user Y/N ");
                String choice = Config.validateString();
                if (choice.equalsIgnoreCase("Y")) {
                    userController.deleteById(id);
                }
                if (choice.equalsIgnoreCase("N")) {
                    new UserManagementView();
                }
            } else {
                System.out.println(Constant.ID_NOT_EXISTED);
                new UserManagementView();
            }
        }
    }

    public void changeUserRole() {
        displayUserListIgnoreAdmin();
        System.out.println("Enter an Id of user you want to edit ");
        int id = Config.validateInt();
        User user = userController.findUserDetailById(id);
        if (user != null) {
            Set<Role> roleSet = user.getRoles();
            List<Role> roles = new ArrayList<>(roleSet);
            if (roles.get(0).getName() != RoleName.ADMIN) {
                System.out.printf("User: %s - Role: %s \n", user.getName(), roles.get(0).getName());
                while (true) {
                    if (roles.get(0).getName() == RoleName.USER) {
                        System.out.println("Do you want to change role into PM Y/N ?");
                    } else {
                        System.out.println("Do you want to change role into User Y/N");
                    }
                    String choice = Config.validateString();
                    if (choice.equalsIgnoreCase("Y")) {
                        userController.updateUser(user, 1);
                    }
                    if (choice.equalsIgnoreCase("N")) {
                        new UserManagementView();
                        break;
                    } else {
                        System.out.println("Can not solve !");
                        new UserManagementView();
                    }

                }
            }
        }
    }

    public void blockUser() {
        Set<Role> roleSet = currentUserList.get(0).getRoles();
        List<Role> roleList = new ArrayList<>(roleSet);
        if (roleList.get(0).getName() == RoleName.ADMIN||roleList.get(0).getName()==RoleName.PM) {
            displayUserListIgnoreAdmin();
            System.out.println("Enter an Id you want to block/unblock ? ");
            int id = Config.validateInt();
            User user = userController.findUserDetailById(id);
            if (user != null) {
                if (user.isStatus()) {
                    System.out.println("Are you sure to unblock this account: Y/N ?");
                } else {
                    System.out.println("Are you sure to block this account: Y/N ?? ");
                }
                String choice = Config.validateString();
                if (choice.equalsIgnoreCase("Y")) {
                    userController.updateUser(user, 2);
                    System.out.println(Constant.CHANGE_SUCCESS);
                    new UserManagementView();
                }
                if (choice.equalsIgnoreCase("N")) {
                    new UserManagementView();
                }
            } else {
                System.out.println(Constant.ID_NOT_EXISTED);
                new UserManagementView();
            }
        }
    }

    public void changePassword() {
        User user = currentUserList.get(0);
        String newPassword;
        boolean flag;
        do {
            System.out.println("Enter the new password you want to change ");
            newPassword = Config.scanner().nextLine();
            flag = Config.validatePassword(newPassword);
            if (!flag) {
                System.out.println(Config.FORMAT_ALERT + " Mật khẩu không được để trống, Có 1 chữ hoa, Có kí tự số, 1 ký tự đặc biệt, và các ký tự thường, tối đa 10 ký tự. ");
            }
        } while (!flag);
        user.setPassword(newPassword);
        userController.updateUser(user, 0);
        userController.updateUserLogin(user);
        System.out.println(Constant.EDIT_SUCCESS);
    }
}
