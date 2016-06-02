package de.hska.iwi.controller;

import de.hska.iwi.db.RoleDAO;
import de.hska.iwi.db.UserDAO;
import de.hska.iwi.domain.Role;
import de.hska.iwi.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserDAO userDAO = new UserDAO();
    private RoleDAO roleDAO = new RoleDAO();

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    private User createUser(@RequestBody User user) {
        userDAO.saveObject(user);
        return userDAO.getUserByUsername(user.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{name}")
    private User getUser(@PathVariable String name) {
        return userDAO.getUserByUsername(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/role/{level}")
    private Role getRoleByLevel(@PathVariable int level) {
        return roleDAO.getRoleByLevel(level);
    }

}
