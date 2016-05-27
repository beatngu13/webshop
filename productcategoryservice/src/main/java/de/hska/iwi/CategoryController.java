package de.hska.iwi;


import de.hska.iwi.domain.Category;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class CategoryController {

    private RestTemplate restTemplate = new RestTemplate();

    private String categoryUrl = "http://localhost:8080/category";

    @RequestMapping(method = RequestMethod.GET, value = "/category")
    public List<Category> getCategories() {
        return Arrays.asList(restTemplate.getForObject(categoryUrl, Category[].class));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return restTemplate.getForObject(categoryUrl + "/" + name, Category.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/category")
    public Category addCategory(@RequestBody Category request) {
        return restTemplate.postForObject(categoryUrl, request, Category.class);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/category/{id}")
    public void removeCategory(@PathVariable int id) {
        restTemplate.delete(categoryUrl + "/" + id);
    }

}
