package view.admin;

import config.Config;
import config.Constant;
import controller.CategoryController;
import dto.response.ResponseMessage;
import model.Category;

import java.util.List;
import java.util.Scanner;

public class CategoryView {
    CategoryController categoryController = new CategoryController();
    List<Category> categoryList = categoryController.getCategoryList();

    public CategoryView() {
        while (true) {
            System.out.println("1. Show List Category ");
            System.out.println("2. Create Category ");
            System.out.println("3. Update Category ");
            System.out.println("4. Delete Category ");
            System.out.println("5. Category Detail ");
            System.out.println("6. Back ");
            System.out.println(" Enter your choice ");
            int choice = Config.scanner().nextInt();
            switch (choice) {
                case 1:
                    showCategoryList();
                    break;
                case 2:
                    createCategory();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 5:
                    showCategoryDetail();
                    break;
                case 6:
                    new ProfileView();
                    break;
            }
        }
    }

    public void showCategoryList() {
        while (true) {
            System.out.println("Category List ");
            for (Category category : categoryList) {
                System.out.printf("ID: %d- Name: %s\n", category.getId(), category.getName());
            }
            System.out.println("Enter back to comeback to Menu ");
            String backMenu = Config.scanner().nextLine();
            if (backMenu.equalsIgnoreCase("back")) {
                new CategoryView();
                break;
            }
        }
    }

    public void createCategory() {
        int id;
        if (categoryList.size() == 0) {
            id = 1;
        } else {
            id = categoryList.get(categoryList.size() - 1).getId() + 1;
        }
        System.out.println("Create Category ");
        System.out.println(" Enter Category Name ");
        String name = Config.validateString();
        Category category = new Category(id, name);
        while (true) {
            ResponseMessage responseMessage = new CategoryController().createCategory(category);
            if (responseMessage.getMessage().equals(Constant.CATEGORY_EXISTED)) {
                System.err.println("Category existed ! ");
                System.out.println("Enter category's name ");
                name = Config.validateString();
                category.setName(name);
            } else {
                System.out.println("Create Successful ! ");
                new CategoryView();
                break;
            }
        }
    }

    public void updateCategory() {
        System.out.println("Edit Category ");
        System.out.println("Category List ");
        for (Category category : categoryList) {
            System.out.printf("ID: %d - Name: %s\n", category.getId(), category.getName());
        }
        while (true) {
            System.out.println("Enter an Id you want to edit: ");
            int id = Config.validateInt();
            boolean flag = false;
            Category category = categoryController.findCategoryById(id);
            if (category != null) {
                System.out.println("Enter a new category name: ");
                String name = Config.validateString();
                category.setName(name);
                while (true) {
                    ResponseMessage responseMessage = new CategoryController().createCategory(category);
                    if (responseMessage.getMessage().equals(Constant.CATEGORY_EXISTED)) {
                        System.err.println("Category Existed ! ");
                        System.out.println("Enter the category name: ");
                        name = Config.validateString();
                        category.setName(name);
                    } else {
                        flag = true;
                    }
                    if (flag) {
                        System.out.println("Edit Successful ! ");
                        new CategoryView();
                        break;
                    }
                }
            }
            System.out.println(Constant.ID_NOT_EXISTED);
        }
    }

    public void deleteCategory() {
        boolean flag = false;
        System.out.println("Category List: ");
        for (Category category : categoryList) {
            System.out.printf("ID: %d -  Name: %s \n", category.getId(), category.getName());
        }
        while (true) {
            System.out.println("Enter the Id you want to delete category ");
            int id = Config.validateInt();
            Category category = categoryController.findCategoryById(id);
            if (category != null) {
                while (true) {
                    System.out.println("Are you sure want to delete this category Y/N");
                    String choice = Config.scanner().nextLine();
                    if (choice.equalsIgnoreCase("Y")) {
                        categoryController.deleteCategoryById(id);
                        flag = true;
                        break;
                    }
                    if (choice.equalsIgnoreCase("N")) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                new CategoryView();
                break;
            }
            System.err.println(Constant.ID_NOT_EXISTED);
        }
    }

    public void showCategoryDetail() {
        while (true) {
            System.out.println("Enter an Id you want to see the details ");
            int id = Config.validateInt();
            if (categoryController.findCategoryById(id) != null) {
                System.out.println(categoryController.findCategoryById(id));
            } else {
                System.err.println(Constant.ID_NOT_EXISTED);
            }
            System.out.println(Constant.BACK_MENU);
            String backMenu = Config.scanner().nextLine();
            if (backMenu.equalsIgnoreCase("back")) {
                new CategoryView();
                break;
            }
        }
    }
}
