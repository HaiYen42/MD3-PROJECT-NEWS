package controller;

import config.Constant;
import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessage;
import model.Role;
import model.RoleName;
import model.User;
import service.role.IRoleService;
import service.role.RoleServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {
    private IUserService userService = new UserServiceIMPL();
    private IRoleService roleService = new RoleServiceIMPL();

    public ResponseMessage register(SignUpDTO data) {
        if (userService.existedByUserName(data.getUsername())) {
            return new ResponseMessage(Constant.USERNAME_EXISTED);
        }
        if (userService.existedByEmail(data.getEmail())) {
            return new ResponseMessage(Constant.EMAIL_EXISTED);
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.findByName(RoleName.USER));
        User user = new User(data.getId(), data.getName(), data.getUsername(), data.getEmail(), data.getPassword(),roleSet);
        userService.save(user);
        return new ResponseMessage(Constant.CREATE_SUCCESS);
    };
    public List<User> getListUser(){
        return userService.findAll();
    }
    public ResponseMessage login(SignInDTO data){
        if (userService.checkLogin(data.getUsername(), data.getPassword())) {
            return new ResponseMessage(Constant.LOGIN_SUCCESSFUL);
        } else {
            return new ResponseMessage(Constant.LOGIN_FAILED);
        }
    }
    public User getUserLogin(){
        return userService.getCurrentUser();
    }
    public void logout(){
        userService.logout();
    }
    public User findUserDetailById(int id){
        return userService.findById(id);
    }
    public void deleteById(int id){
         userService.deleteById(id);
    }
    public void updateUserLogin(User user){
        userService.updateUserLogin(user);
    }
    public void updateUser(User user, int method){
        // Method để phân biệt cập nhật thông tin(0), change role(1) và block user(2)
        if (method == 0) {
            userService.save(user);
        }
        if (method == 1) {
            Set<Role> roleSet = user.getRoles();
            List<Role> roleList = new ArrayList<>(roleSet);
            if (roleList.get(0).getName()==RoleName.USER) {
                Set<Role> newRoleSet = new HashSet<>();
                newRoleSet.add(roleService.findByName(RoleName.PM));
                user.setRoles(newRoleSet);
                userService.save(user);
            } else if (roleList.get(0).getName()==RoleName.PM) {
                Set<Role> newRoleSet = new HashSet<>();
                newRoleSet.add(roleService.findByName(RoleName.USER));
                user.setRoles(newRoleSet);
                userService.save(user);
            }
        }
        if (method == 2) {
            user.setStatus(!user.isStatus());
            userService.save(user);
        }
    }
}
