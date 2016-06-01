package de.hska.iwi.controller;

import de.hska.iwi.db.CategoryDAO;
import de.hska.iwi.domain.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryDAO categoryDAO = new CategoryDAO();

    @RequestMapping(method = RequestMethod.GET, value = "/category")
    public List<Category> getCategories() {
        return categoryDAO.getObjectList();
    }
    
    /*
    @RequestMapping(method = RequestMethod.GET, value = "/category/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryDAO.getObjectByName(name);
    }
    */
    
    @RequestMapping(method = RequestMethod.GET, value = "/category/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryDAO.getObjectById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/category")
    public Category addCategory(@RequestBody Category request) {
        categoryDAO.saveObject(request);
        return categoryDAO.getObjectByName(request.getName());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/category/{id}")
    public void removeCategory(@PathVariable int id) {
        categoryDAO.deleteById(id);
    }

}
