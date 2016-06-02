package hska.iwi.shopui.model.businessLogic.manager.impl;

import static hska.iwi.shopui.model.businessLogic.manager.impl.Configuration.ROLE;
import static hska.iwi.shopui.model.businessLogic.manager.impl.Configuration.USER;
import static hska.iwi.shopui.model.businessLogic.manager.impl.Configuration.USER_BASE_URL;

import org.springframework.web.client.RestTemplate;

import hska.iwi.shopui.model.businessLogic.manager.UserManager;
import hska.iwi.shopui.model.domain.Role;
import hska.iwi.shopui.model.domain.User;

public class UserManagerImpl implements UserManager {
	private RestTemplate restTemplate;
	
	public UserManagerImpl() {
		restTemplate = new RestTemplate();
	}

	public void registerUser(String username, String name, String lastname, String password, Role role) {
		User user = new User(username, name, lastname, password, role);
		restTemplate.postForObject(USER_BASE_URL + USER, user, User.class);
	}

	
	public User getUserByUsername(String username) {
		if (username == null || username.equals("")) {
			return null;
		}
		
		return restTemplate.getForObject(USER_BASE_URL + USER + "/" + username, User.class);
	}

	public Role getRoleByLevel(int level) {
		return restTemplate.getForObject(USER_BASE_URL + ROLE + "/" + level, Role.class);
	}

	public boolean doesUserAlreadyExist(String username) {
    	User dbUser = this.getUserByUsername(username);
    	
    	if (dbUser != null){
    		return true;
    	} else {
    		return false;
    	}
	}
	
	public boolean validate(User user) {
		if (user.getFirstname().isEmpty() || user.getPassword().isEmpty() || user.getRole() == null || user.getLastname() == null || user.getUsername() == null) {
			return false;
		}
		
		return true;
	}
}
