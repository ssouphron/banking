package com.esgi.controller;

import com.esgi.crm.User;
import com.esgi.dto.UserDTO;
import com.esgi.error.NotFoundException;
import com.esgi.helper.DTOHelper;
import com.esgi.service.crm.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get user data
     */
    @RequestMapping(method = GET, value = "/users/{id}")
    @ResponseStatus(OK)
    public UserDTO getUser(@PathVariable(value = "id") final Integer userId) throws NotFoundException {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        return DTOHelper.getUserDTO(user);
    }

    /**
     * Get all users list
     */
    @RequestMapping(method = GET, value = "/users")
    @ResponseStatus(OK)
    public List<UserDTO> getUsers() {
        List<User> users = userService.getAllUsers();
        return DTOHelper.getUserDTO(users);
    }
}
