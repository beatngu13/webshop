package de.hska.iwi;

import de.hska.iwi.domain.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class CategoryController {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private LoadBalancerClient loadBalancer;

    private String getCategoryUrl() {
    	ServiceInstance instance = loadBalancer.choose("category-service");
    	return String.format("http://%s:%s/category", instance.getHost(), instance.getPort());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category")
    public List<Category> getCategories() {
        return Arrays.asList(restTemplate.getForObject(getCategoryUrl(), Category[].class));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return restTemplate.getForObject(getCategoryUrl() + "/" + name, Category.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/category")
    public Category addCategory(@RequestBody Category request) {
        return restTemplate.postForObject(getCategoryUrl(), request, Category.class);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/category/{id}")
    public void removeCategory(@PathVariable int id) {
        restTemplate.delete(getCategoryUrl() + "/" + id);
    }
}
