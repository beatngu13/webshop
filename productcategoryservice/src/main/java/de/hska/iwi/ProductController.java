package de.hska.iwi;

import de.hska.iwi.domain.response.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private RestTemplate restTemplate = new RestTemplate();
    private CategoryController categoryController = new CategoryController();

    @Autowired
    private LoadBalancerClient loadBalancer;

    private String getProductUrl() {
    	ServiceInstance instance = loadBalancer.choose("product-service");
    	return String.format("http://%s:%s/product", instance.getHost(), instance.getPort());
    }

    @RequestMapping(method = RequestMethod.POST)
    private Product createProduct(@RequestBody Product product) {
        return restTemplate.postForObject(getProductUrl(), product, Product.class);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    private Product getProduct(@PathVariable int id) {
        return restTemplate.getForObject(getProductUrl() + "/" + id, Product.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<Product> searchProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getProductUrl())
                .queryParam("name", name)
                .queryParam("minPrice", minPrice)
                .queryParam("maxPrice", maxPrice);

        List<de.hska.iwi.domain.Product> products =
                Arrays.asList(restTemplate.getForObject(builder.build().encode().toUri(), de.hska.iwi.domain.Product[].class));

        return convert(products);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    private void deleteProduct(@PathVariable int id) {
        restTemplate.delete(getProductUrl() + "/" + id);
    }

    private List<Product> convert(List<de.hska.iwi.domain.Product> products) {
        List<Product> res = new ArrayList<>(products.size());

        for (de.hska.iwi.domain.Product p : products) {
            res.add(convert(p));
        }

        return res;
    }

    private Product convert(de.hska.iwi.domain.Product product) {
        return new Product(product.getName(),
                product.getPrice(),
                categoryController.getCategoryById(product.getCategory()),
                product.getDetails());
    }

}
