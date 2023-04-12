package service.category;

import config.Config;
import config.Constant;
import model.Category;

import java.util.List;

public class CategoryServiceIMPL implements ICategoryService {
    List<Category> categoryList = new Config<Category>().readFromFile(Constant.PATH_CATEGORY);

    @Override
    public List<Category> findAll() {
        return categoryList;
    }

    @Override
    public void save(Category category) {
        Category newCategory = findById(category.getId());
        if (newCategory == null) {
            categoryList.add(category);
        } else {
            categoryList.set(categoryList.indexOf(newCategory), category);
        }
        new Config<Category>().writeToFile(Constant.PATH_CATEGORY, categoryList);
    }

    @Override
    public Category findById(int id) {
        for (Category category : categoryList) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        Category categoryDelete = findById(id);
        int index = categoryList.indexOf(categoryDelete);
        categoryList.remove(index);
        new Config<Category>().writeToFile(Constant.PATH_CATEGORY, categoryList);
    }

    @Override
    public boolean existByName(String name) {
        for (Category category : categoryList) {
            if (name.equalsIgnoreCase(category.getName())) {
                return true;
            }
        }
        return false;
    }

}
