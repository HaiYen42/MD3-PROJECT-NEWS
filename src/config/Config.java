package config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Config<T> {
    public static Scanner scanner() {
        Scanner scanner = new Scanner(System.in);
        return scanner;
    }

    public static final String PATH_USER = "src/database/user.txt";
    public static final String PATH_USER_LOGIN= "src/database/user_principal.txt";

    public static final String FORMAT_ALERT= "Format Incorrect !!!";
    public static final String EMPTY_ALERT= "Can't Be Empty !";
    public static final String SUCCESS_ALERT= " Successful !";

    public static int validateInt(){
        int data=0;
        while (true){
            try {
                data = Integer.parseInt(scanner().nextLine());
                return data;
            }catch (NumberFormatException e){
                System.err.println(FORMAT_ALERT);
            }
        }
    }
    public static double validateDouble(){
        double data=0;
        while (true){
            try {
                data= Double.parseDouble(scanner().nextLine());
                return data;
            }catch (Exception e){
                System.err.println(FORMAT_ALERT);
            }
        }
    }
    public static String validateString(){
        while (true){
            String result= scanner().nextLine();
            if (result.equals("")) {
                System.err.println(EMPTY_ALERT);
                continue;
            }
            return result;
        }
    }
    public static boolean validatePassword(String data){
        final String PASSWORD_REGEX ="^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{1,10}$";
        return Pattern.matches(PASSWORD_REGEX, data);
    }
    public static boolean validateEmail(String data){
        final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(EMAIL_REGEX, data);
    }
    public static boolean validateName(String data){
        final String NAME_REGEX = ".{1,40}";
        return Pattern.matches(NAME_REGEX,data);
    }
    public static boolean validateUsername(String data){
        final String USERNAME_REGEX = "^\\S{1,30}";
        return Pattern.matches(USERNAME_REGEX, data);
    }

    //Đọc file
    public List<T> readFromFile(String pathFile) {
        List<T> tList = new ArrayList<>();
        try {
            File file = new File(pathFile);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                tList= (List<T>) objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException");
        }catch (IOException e){
            System.err.println("IOException");
        }catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException");
        }
        return tList;
    }
    public void writeToFile(String pathFile, List<T> tList){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pathFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tList);
            fileOutputStream.close();
            objectOutputStream.close();
        }catch (FileNotFoundException e){
            System.err.println("FileNotFoundException");
        }catch (IOException e){
            System.err.println("IOException");
        }
    }
}
