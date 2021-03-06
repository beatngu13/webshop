package hska.iwi.shopui.model.businessLogic.manager;

import java.util.List;

import hska.iwi.shopui.model.domain.Product;

public interface ProductManager {

	public List<Product> getProducts();

	public Product getProductById(int id);

	public Product getProductByName(String name);

	public int addProduct(String name, double price, int categoryId, String details);

	public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice);
	
    public void deleteProductById(int id);
    
	
}
