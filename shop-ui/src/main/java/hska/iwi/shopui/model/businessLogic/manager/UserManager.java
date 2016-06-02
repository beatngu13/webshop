package hska.iwi.shopui.model.businessLogic.manager;

import hska.iwi.shopui.model.domain.Role;
import hska.iwi.shopui.model.domain.User;


public interface UserManager {
    
    public void registerUser(String username, String name, String lastname, String password, Role role);
    
    public User getUserByUsername(String username);
    
    public Role getRoleByLevel(int level);
    
    public boolean doesUserAlreadyExist(String username);
}
