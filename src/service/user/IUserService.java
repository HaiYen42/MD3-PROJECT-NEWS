package service.user;

import model.User;
import service.IGenericService;

public interface IUserService extends IGenericService<User> {
    boolean existedByUserName(String userName);
    boolean existedByEmail(String email);
    boolean checkLogin(String username, String password);
    User getCurrentUser();
    void logout();
    void updateUserLogin(User user);
}
