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
	
	private class ProductRequest {
		private int id;
		private String name;
		private double price;
		private int category;
		private String details;

		public ProductRequest(String name, double price, int category) {
			this.name = name;
			this.price = price;
			this.category = category;
		}

		public ProductRequest(String name, double price, int category, String details) {
			this.name = name;
			this.price = price;
			this.category = category;
			this.details = details;
		}

		public int getId() {
			return this.id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPrice() {
			return this.price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public int getCategory() {
			return this.category;
		}

		public void setCategory(int category) {
			this.category = category;
		}

		public String getDetails() {
			return this.details;
		}

		public void setDetails(String details) {
			this.details = details;
		}
	}
	
	private RestTemplate restTemplate;
	private CategoryManager categoryManager;
	
	public ProductManagerImpl() {
		restTemplate = new RestTemplate();
		categoryManager = new CategoryManagerImpl();
	}

	public List<Product> getProducts() {
		List<ProductRequest> productRequests = Arrays.asList(restTemplate.getForObject(PRODUCTCATEGORY_BASE_URL + PRODUCT, ProductRequest[].class));
		List<Product> products = new ArrayList<Product>(productRequests.size());
		
		productRequests.stream().forEach(productRequest -> {
			Category category = categoryManager.getCategory(productRequest.category);
			products.add(new Product(productRequest.getName(), productRequest.getPrice(), category, productRequest.getDetails()));
		});
		
		return products;
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
		
		CategoryManager categoryManager = new CategoryManagerImpl();
		Category category = categoryManager.getCategory(categoryId);
		
		if(category != null){
			ProductRequest product;
			if(details == null){
				product = new ProductRequest(name, price, category.getId());	
			} else{
				product = new ProductRequest(name, price, category.getId(), details);
			}
			
			restTemplate.postForObject(PRODUCTCATEGORY_BASE_URL + PRODUCT, product, Product.class);
			productId = product.getId();
		}
			 
		return productId;
	}
	
	public void deleteProductById(int id) {
		restTemplate.delete(PRODUCTCATEGORY_BASE_URL + PRODUCT + "/" + id);
	}
}
