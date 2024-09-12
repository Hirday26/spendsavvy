package com.project.expensetracker.web;

import com.project.expensetracker.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserResource {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> registerUser(@RequestBody Map<String,Object> body)
    {
        return userService.registerUser(body);
    }
    @PostMapping("/login")
    public Map<String,Object> loginUser(@RequestBody Map <String,Object> body)
    {
        System.out.println(userService.loginUser(body));
        return userService.loginUser(body);
    }
//    getting income types that are required while registering
    @GetMapping("/incomeTypes")
    public List<Map<String , Object>> getIncomeTypes()
    {
        System.out.println(userService.getIncomeTypes());
        return userService.getIncomeTypes();
    }




}

