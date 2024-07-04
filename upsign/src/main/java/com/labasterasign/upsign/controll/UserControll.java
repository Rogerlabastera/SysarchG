package com.labasterasign.upsign.controll;

import com.labasterasign.upsign.model.User;
import com.labasterasign.upsign.repository.UserRepo;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
public class UserControll {



    @Autowired
    private UserRepo userRepo;


    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException{
        response.sendRedirect("/swagger-ui.html");
    }


    // Get all users
    @GetMapping("/Users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // User signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (isEmpty(user.getUsername(), user.getPassword(), user.getEmail(), user.getConfirmPass())) {
            return ResponseEntity.badRequest().body("All fields must be filled");
        }

        if (userRepo.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User existingUser = userRepo.findByUsername(username);

        if (existingUser != null && existingUser.getPassword().equals(password)) {
            return ResponseEntity.ok("Login Success");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Helper method to check if any string is empty
    private boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}


