package de.hska.iwi;

import de.hska.iwi.domain.CoreProduct;
import de.hska.iwi.domain.UIProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final String PRODUCT_URL = "http://localhost:8081/product";

    @Autowired
    private CategoryController categoryController;
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(method = RequestMethod.POST)
    private UIProduct createProduct(@RequestBody UIProduct product) {
        CoreProduct coreProduct = convertForCoreService(product);
        return convertForUI(restTemplate.postForObject(PRODUCT_URL, coreProduct, CoreProduct.class));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    private UIProduct getProduct(@PathVariable int id) {
        return convertForUI(restTemplate.getForObject(PRODUCT_URL + "/" + id, CoreProduct.class));
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<UIProduct> searchProduct(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "minPrice", required = false) Double minPrice,
                                          @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PRODUCT_URL)
                .queryParam("name", name)
                .queryParam("minPrice", minPrice)
                .queryParam("maxPrice", maxPrice);
        List<CoreProduct> products =
                Arrays.asList(restTemplate.getForObject(builder.build().encode().toUri(), CoreProduct[].class));

        return convertForUI(products);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    private void deleteProduct(@PathVariable int id) {
        restTemplate.delete(PRODUCT_URL + "/" + id);
    }

    private List<UIProduct> convertForUI(List<CoreProduct> products) {
        return products.stream().map(p -> convertForUI(p)).collect(Collectors.toList());
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
