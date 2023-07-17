package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping( path = "/users")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) { this.userDao = userDao; }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() { return userDao.findAll();}

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public User getById(@PathVariable int id, Principal principal) {
        return userDao.getUserById(id);
    }
}
