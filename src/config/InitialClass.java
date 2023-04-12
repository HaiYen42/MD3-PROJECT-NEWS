package config;

import model.Role;
import model.RoleName;
import model.User;
import service.role.IRoleService;
import service.role.RoleServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InitialClass {
    static IRoleService roleService = new RoleServiceIMPL();
    static IUserService userService = new UserServiceIMPL();
    static List<User> listUserLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);

//    public static void main(String[] args) {
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(roleService.findByName(RoleName.ADMIN));
//        userService.save(new User(0, "admin", "admin", "admin@gmail.com", "admin", "", false, roleSet));
//    }
}
