package com.example.crud_user.controller;

import com.example.crud_user.model.User;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.crud_user.service.UserService;
import java.util.List;


@RestController
@ApiResponse(description = "Контроллер для User")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    //@ApiResponse
    public ResponseEntity<List<User>> readAll(){
        final List <User> users = userService.findAll();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK) // нужен ли лист? Нельзя так: ? new ResponseEntity<>(userService.findAll(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND); // если можно, то какой тип указать вместо Т в ResponseEntity<Т>?
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<User> readById(@PathVariable("id") Long id){
        User user = userService.findById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/user-create")
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "user-delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        final boolean deleted = userService.deleteById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PutMapping(value = "/user-update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser){
        final boolean updated = userService.updateUser(id, updatedUser);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
