package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

//This is a class defining an Account Controller with several HTTP request handlers for interacting with accounts:
//The getBalance method handles HTTP GET requests to "/account/" to retrieve the account balance of the currently authenticated user.
//The getUser method handles HTTP GET requests to "/user/" to retrieve the user information of the currently authenticated user.
//The getUserAccount method handles HTTP GET requests to "/user/account/" to retrieve the account information of the currently authenticated user.
//The getUsers method handles HTTP GET requests to "/user/allusers/" to retrieve a list of all users with their associated account information.
@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private UserDao userDao;

    public AccountController (UserDao userDao){
        this.userDao = userDao;
    }

    @RequestMapping(path = "/account/", method = RequestMethod.GET)
    public BigDecimal getBalance (Principal principal) {
        int id = userDao.findIdByUsername(principal.getName());
        return userDao.getAccountBalance(id);
    }

    @RequestMapping (path = "/user/", method = RequestMethod.GET)
    public User getUser(Principal principal){
        return userDao.findByUsername(principal.getName());
    }

    @RequestMapping (path = "/user/account/", method = RequestMethod.GET)
    public User getUserAccount(Principal principal){
        return userDao.findAccountByUsername(principal.getName());
    }

    @RequestMapping (path = "/user/allusers/", method = RequestMethod.GET)
    public List<User> getUsers (Principal principal) {
        return userDao.findAllUsersWithAccount(principal.getName());
    }
}
