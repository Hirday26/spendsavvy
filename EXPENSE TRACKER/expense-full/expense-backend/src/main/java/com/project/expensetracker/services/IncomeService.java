package com.project.expensetracker.services;

import com.project.expensetracker.repo.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IncomeService {
    @Autowired
    IncomeRepository incomeRepository;

    public ResponseEntity<Map<String,Object>> addIncome(Map<String,Object> body) {
        System.out.println(body);
        int userId = (int) body.get("userId");
        int incomeAmount = (int) body.get("incomeAmount");
        int incomeType = (int) body.get("incomeType");
        int rowsAffected =  incomeRepository.addIncome(userId, incomeAmount, incomeType);
        if(rowsAffected > 0) {
            return ResponseEntity.ok(Map.of("status", "success adding income"));
        }
        return ResponseEntity.status(401).body(Map.of("status", "error adding income"));
    }
    public Map<String,Object> getTotalMonthlyIncome(int userId) {
        return incomeRepository.getTotalMonthlyIncome(userId);
    }
    public List<Map<String,Object>> getIncome(int userId) {
        return incomeRepository.getIncome(userId);
    }
}
