package de.hska.iwi;


import de.hska.iwi.domain.Category;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final String CATEGORY_URL = "http://localhost:8082/category";

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getCategories() {
        return Arrays.asList(restTemplate.getForObject(CATEGORY_URL, Category[].class));
    }

    /*
    @RequestMapping(method = RequestMethod.GET, value = "/category/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return restTemplate.getForObject(CATEGORY_URL + "/" + name, Category.class);
    }
	*/

    @RequestMapping(method = RequestMethod.POST)
    public Category addCategory(@RequestBody Category request) {
        return restTemplate.postForObject(CATEGORY_URL, request, Category.class);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void removeCategory(@PathVariable int id) {
        restTemplate.delete(CATEGORY_URL + "/" + id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return restTemplate.getForObject(CATEGORY_URL + "/" + id, Category.class);
    }

}
