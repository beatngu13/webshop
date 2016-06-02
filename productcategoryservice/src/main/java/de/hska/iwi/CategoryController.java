package de.hska.iwi;

import de.hska.iwi.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private LoadBalancerClient loadBalancer;

    private String getCategoryUrl() {
        ServiceInstance instance = loadBalancer.choose("category-service");
        return String.format("http://%s:%s/category", instance.getHost(), instance.getPort());
    }
    
    @HystrixCommand(fallbackMethod = "getCategoriesFallback")
    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getCategories() {
        return Arrays.asList(restTemplate.getForObject(getCategoryUrl(), Category[].class));
    }
    
    public List<Category> getCategoriesFallback() {
    	return Arrays.asList(new Category("Error"));
    }

    @HystrixCommand(fallbackMethod = "addCategoryFallback")
    @RequestMapping(method = RequestMethod.POST)
    public Category addCategory(@RequestBody Category request) {
        return restTemplate.postForObject(getCategoryUrl(), request, Category.class);
    }
    
    public Category addCategoryFallback(Category request) {
    	return new Category("Error");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void removeCategory(@PathVariable int id) {
        restTemplate.delete(getCategoryUrl() + "/" + id);
    }

    @HystrixCommand(fallbackMethod = "getCategoryByIdFallback")
    @Cacheable("categories")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return restTemplate.getForObject(getCategoryUrl() + "/" + id, Category.class);
    }

    public Category getCategoryByIdFallback(int id) {
    	return new Category("Error");
    }
}
