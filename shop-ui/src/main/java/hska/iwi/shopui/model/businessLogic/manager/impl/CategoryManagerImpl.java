package hska.iwi.shopui.model.businessLogic.manager.impl;

import static hska.iwi.shopui.model.businessLogic.manager.impl.Configuration.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import hska.iwi.shopui.model.businessLogic.manager.CategoryManager;
import hska.iwi.shopui.model.domain.Category;

public class CategoryManagerImpl implements CategoryManager{
	private RestTemplate restTemplate;
	
	public CategoryManagerImpl() {
		restTemplate = new RestTemplate();
	}

	public List<Category> getCategories() {
		return Arrays.asList(restTemplate.getForObject(PRODUCTCATEGORY_BASE_URL + CATEGORY, Category[].class));
	}

	public Category getCategory(int id) {
		return restTemplate.getForObject(PRODUCTCATEGORY_BASE_URL + CATEGORY + "/" + id, Category.class);
	}

	public Category getCategoryByName(String name) {
		return restTemplate.getForObject(PRODUCTCATEGORY_BASE_URL + CATEGORY + "/" + name, Category.class);
	}

	public void addCategory(String name) {
		Category cat = new Category(name);
		restTemplate.postForObject(PRODUCTCATEGORY_BASE_URL + CATEGORY, cat, Category.class);
	}

	public void delCategory(Category cat) {
		delCategoryById(cat.getId());
	}

	public void delCategoryById(int id) {
		restTemplate.delete(PRODUCTCATEGORY_BASE_URL + CATEGORY + "/" + id);
	}
}
