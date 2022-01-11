package com.pk.project_io.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
        service.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name) {
        User user = service.getUserByName(name);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
