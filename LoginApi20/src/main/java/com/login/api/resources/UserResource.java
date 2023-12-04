package com.login.api.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.login.api.user.entity.User;
import com.login.api.user.service.UserService;


@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers()
    {
        log.debug("CLIENT REST REQUEST!");

        List<User> userList = userService.findAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
        
    }
    
}
