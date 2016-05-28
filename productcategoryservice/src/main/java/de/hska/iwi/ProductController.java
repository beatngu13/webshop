package de.hska.iwi;

import de.hska.iwi.domain.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private RestTemplate restTemplate = new RestTemplate();
    
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

        return Arrays.asList(restTemplate.getForObject(builder.build().encode().toUri(), Product[].class));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    private void deleteProduct(@PathVariable int id) {
        restTemplate.delete(getProductUrl() + "/" + id);
    }
}
