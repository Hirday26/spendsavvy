package com.project.expensetracker.web;

import com.project.expensetracker.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class IncomeResource {
    @Autowired
    IncomeService incomeService;
    @PostMapping("/add-income")
    public ResponseEntity<Map<String,Object>> addIncome(@RequestBody Map<String,Object> body)  {
        return incomeService.addIncome(body);
    }
    @GetMapping("/monthly-total-income/{userId}")
    public Map<String,Object> getTotalMonthlyIncome(@PathVariable int userId) {
        return incomeService.getTotalMonthlyIncome(userId);
    }
    @GetMapping("/income/{userId}")
    public List<Map<String, Object>> getIncome(@PathVariable int userId) {
        return incomeService.getIncome(userId);
    }
}
