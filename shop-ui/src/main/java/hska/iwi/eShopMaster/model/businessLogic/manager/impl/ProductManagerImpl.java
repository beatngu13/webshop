package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import static hska.iwi.eShopMaster.model.businessLogic.manager.impl.Configuration.PRODUCT;
import static hska.iwi.eShopMaster.model.businessLogic.manager.impl.Configuration.PRODUCTCATEGORY_BASE_URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.domain.Category;
import hska.iwi.eShopMaster.model.domain.Product;

public class ProductManagerImpl implements ProductManager {

	private RestTemplate restTemplate;
	private CategoryManager categoryManager;

	public ProductManagerImpl() {
		restTemplate = new RestTemplate();
		categoryManager = new CategoryManagerImpl();
	}

	public List<Product> getProducts() {
		return Arrays.asList(restTemplate.getForObject(PRODUCTCATEGORY_BASE_URL + PRODUCT, Product[].class));
	}

	public List<Product> getProductsForSearchValues(String searchDescription,
													Double searchMinPrice, Double searchMaxPrice) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PRODUCTCATEGORY_BASE_URL + PRODUCT)
				.queryParam("name", searchDescription)
				.queryParam("minPrice", searchMinPrice)
				.queryParam("maxPrice", searchMaxPrice);

		return Arrays.asList(restTemplate.getForObject(builder.build().encode().toUri(), Product[].class));
	}

	public Product getProductById(int id) {
		return restTemplate.getForObject(PRODUCTCATEGORY_BASE_URL + PRODUCT + "/" + id, Product.class);
	}

	public Product getProductByName(String name) {
		return restTemplate.getForObject(PRODUCTCATEGORY_BASE_URL + PRODUCT + "/" + name, Product.class);
	}

	public int addProduct(String name, double price, int categoryId, String details) {
		int productId = -1;

		Category category = categoryManager.getCategory(categoryId);

		if(category != null){
			Product product = (details == null)
					? new Product(name, price, category)
					: new Product(name, price, category, details);

			restTemplate.postForObject(PRODUCTCATEGORY_BASE_URL + PRODUCT, product, Product.class);
			productId = product.getId();
		}

		return productId;
	}

	public void deleteProductById(int id) {
		restTemplate.delete(PRODUCTCATEGORY_BASE_URL + PRODUCT + "/" + id);
	}
}
