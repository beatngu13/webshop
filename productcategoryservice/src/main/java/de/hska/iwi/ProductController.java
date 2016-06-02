package de.hska.iwi;

import de.hska.iwi.domain.Category;
import de.hska.iwi.domain.CoreProduct;
import de.hska.iwi.domain.UIProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CategoryController categoryController;
    @Autowired
    private LoadBalancerClient loadBalancer;

    public String getProductUrl() {
        ServiceInstance instance = loadBalancer.choose("product-service");
        return String.format("http://%s:%s/product", instance.getHost(), instance.getPort());
    }

    @HystrixCommand(fallbackMethod = "createProductFallback")
    @RequestMapping(method = RequestMethod.POST)
    public UIProduct createProduct(@RequestBody UIProduct product) {
        CoreProduct coreProduct = convertForCoreService(product);
        return convertForUI(restTemplate.postForObject(getProductUrl(), coreProduct, CoreProduct.class));
    }
    
    public UIProduct createProductFallback(UIProduct product) {
    	return new UIProduct("Error", 0.0, new Category("Error"));
    }

    @HystrixCommand(fallbackMethod = "getProductFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public UIProduct getProduct(@PathVariable int id) {
        return convertForUI(restTemplate.getForObject(getProductUrl() + "/" + id, CoreProduct.class));
    }
    
    public UIProduct getProductFallback(int id) {
    	return new UIProduct("Error", 0.0, new Category("Error"));
    }

    @HystrixCommand(fallbackMethod = "searchProductFallback")
    @RequestMapping(method = RequestMethod.GET)
    public List<UIProduct> searchProduct(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "minPrice", required = false) Double minPrice,
                                          @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getProductUrl())
                .queryParam("name", name)
                .queryParam("minPrice", minPrice)
                .queryParam("maxPrice", maxPrice);
        List<CoreProduct> products =
                Arrays.asList(restTemplate.getForObject(builder.build().encode().toUri(), CoreProduct[].class));

        return convertForUI(products);
    }
    
    public List<UIProduct> searchProductFallback(String name, Double minPrice, Double maxPrice) {
    	return Arrays.asList(new UIProduct("Error", 0.0, new Category("Error")));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteProduct(@PathVariable int id) {
        restTemplate.delete(getProductUrl() + "/" + id);
    }

    private List<UIProduct> convertForUI(List<CoreProduct> products) {
        return products.stream().map(p -> convertForUI(p)).collect(Collectors.toList());
    }

    private UIProduct convertForUI(CoreProduct product) {
        return new UIProduct(product.getId(),
        		product.getName(),
                product.getPrice(),
                categoryController.getCategoryById(product.getCategory()),
                product.getDetails());
    }

    private CoreProduct convertForCoreService(UIProduct uiProduct) {
        return new CoreProduct(uiProduct.getName(),
                uiProduct.getPrice(),
                uiProduct.getCategory().getId(),
                uiProduct.getDetails());
    }

}
