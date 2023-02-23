package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "user/{id}", method = RequestMethod.GET)
    public User listUserById(@PathVariable Long id) {
        return userDao.findById(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "user/", method = RequestMethod.GET)
    public List<User> listAllUsers() {
        return userDao.findAll();
    }
}
