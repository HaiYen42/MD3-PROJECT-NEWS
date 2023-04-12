package service.user;

import config.Config;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserServiceIMPL implements IUserService {

    List<User> listUser = new Config<User>().readFromFile(Config.PATH_USER);

    List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
    @Override
    public List<User> findAll() {
        return listUser;
    }

    @Override
    public void save(User user) {
        User newUser = findById(user.getId());
        if (newUser == null) {
            listUser.add(user);
        }else {
            int index = listUser.indexOf(newUser);
            listUser.set(index, user);
        }
        new Config<User>().writeToFile(Config.PATH_USER, listUser);
    }

    @Override
    public User findById(int id) {
        for (User user : listUser) {
            if (id == user.getId()) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        int index= listUser.indexOf(findById(id));
        listUser.remove(index);
        new Config<User>().writeToFile(Config.PATH_USER, listUser);
    }

    @Override
    public boolean existedByUserName(String userName) {
        listUser= new Config<User>().readFromFile(Config.PATH_USER);
        for (User user : listUser) {
            if (userName.equalsIgnoreCase(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existedByEmail(String email) {
        listUser= new Config<User>().readFromFile(Config.PATH_USER);
        for (User user : listUser) {
            if (email.equalsIgnoreCase(user.getEmail()) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        List<User> userLogin = new ArrayList<>();
        for (User user : listUser) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                userLogin.add(user);
                new Config<User>().writeToFile(Config.PATH_USER_LOGIN, userLogin);
                return true;
            }
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        if (new Config<User>().readFromFile(Config.PATH_USER_LOGIN).size() != 0) {
            User user = new Config<User>().readFromFile(Config.PATH_USER_LOGIN).get(0);
            return user;
        }
        return null;
    }

    @Override
    public void logout() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Config.PATH_USER_LOGIN);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //Để ghi rỗng vào PATH_USER_LOGIN
            objectOutputStream.write(("").getBytes());
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException");
        } catch (IOException e) {
            System.err.println("IOException");
        }
    }

    @Override
    public void updateUserLogin(User user) {
        userLogin.set(0,user);
        new Config<User>().writeToFile(Config.PATH_USER_LOGIN, userLogin);
    }


}
