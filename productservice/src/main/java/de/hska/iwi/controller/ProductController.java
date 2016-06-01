package de.hska.iwi.controller;

import de.hska.iwi.db.ProductDAO;
import de.hska.iwi.domain.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductDAO productDAO = new ProductDAO();

    @RequestMapping(method = RequestMethod.POST)
    private Product createProduct(@RequestBody Product product) {
        productDAO.saveObject(product);
        return productDAO.getProductByName(product.getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    private Product getProduct(@PathVariable int id) {
        return productDAO.getObjectById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<Product> searchProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        return productDAO.getProductListByCriteria(name, minPrice, maxPrice);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    private void deleteProduct(@PathVariable int id) {
        productDAO.deleteById(id);
    }

}
