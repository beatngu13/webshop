package de.hska.iwi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import de.hska.iwi.domain.CoreProduct;
import de.hska.iwi.domain.UIProduct;

@RestController
@RequestMapping("/product")
public class ProductController {

    private RestTemplate restTemplate = new RestTemplate();
    private String productUrl = "http://localhost:8081/product";

    private CategoryController categoryController = new CategoryController();

    @RequestMapping(method = RequestMethod.POST)
    private UIProduct createProduct(@RequestBody UIProduct product) {     				
    	CoreProduct coreProduct = convertForCoreService(product);
    	return convertForUI(restTemplate.postForObject(productUrl, coreProduct, CoreProduct.class));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    private UIProduct getProduct(@PathVariable int id) {
        return convertForUI(restTemplate.getForObject(productUrl + "/" + id, CoreProduct.class));
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<UIProduct> searchProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(productUrl)
                .queryParam("name", name)
                .queryParam("minPrice", minPrice)
                .queryParam("maxPrice", maxPrice);

        List<CoreProduct> products =
                Arrays.asList(restTemplate.getForObject(builder.build().encode().toUri(), CoreProduct[].class));

        return convertForUI(products);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    private void deleteProduct(@PathVariable int id) {
        restTemplate.delete(productUrl + "/" + id);
    }

    private List<UIProduct> convertForUI(List<CoreProduct> products) {
        List<UIProduct> uiProducts = new ArrayList<>(products.size());

        for (CoreProduct product : products) {
            uiProducts.add(convertForUI(product));
        }

        return uiProducts;
    }

    private UIProduct convertForUI(CoreProduct product) {
        return new UIProduct(product.getName(),
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
