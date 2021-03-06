package hska.iwi.shopui.controller;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import hska.iwi.shopui.model.businessLogic.manager.ProductManager;
import hska.iwi.shopui.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.shopui.model.domain.User;

public class DeleteProductAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666796923937616729L;

	private int id;

	public String execute() throws Exception {
		
		String res = "input";
		
		ProductManager productManager = new ProductManagerImpl();
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.getRole().getTyp().equals("admin"))) {
			productManager.deleteProductById(id);
			res = "success";
		}
		
		return res;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
