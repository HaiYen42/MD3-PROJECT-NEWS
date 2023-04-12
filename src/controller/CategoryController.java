package controller;

import config.Constant;
import dto.response.ResponseMessage;
import model.Category;
import org.omg.CORBA.PUBLIC_MEMBER;
import service.category.CategoryServiceIMPL;
import service.category.ICategoryService;

import java.util.List;

public class CategoryController {
ICategoryService categoryService = new CategoryServiceIMPL();

public List<Category> getCategoryList(){
    return categoryService.findAll();
}
public ResponseMessage createCategory(Category category){
    if (categoryService.existByName(category.getName())) {
        return new ResponseMessage(Constant.CATEGORY_EXISTED);
    } else {
        categoryService.save(category);
        return new ResponseMessage(Constant.CREATE_SUCCESS);
    }
}
public Category findCategoryById(int id){
    return categoryService.findById(id);
}
public void deleteCategoryById(int id){
    categoryService.deleteById(id);
}
public void updateCategory(Category category){
    categoryService.save(category);
}
}
