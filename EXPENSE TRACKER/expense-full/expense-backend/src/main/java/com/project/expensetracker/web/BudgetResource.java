
package com.project.expensetracker.web;

import com.project.expensetracker.services.BudgetService;
import com.project.expensetracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetResource {

    @Autowired
    BudgetService budgetService;
    @PostMapping("/add-budget")
    public ResponseEntity<Map<String ,Object>> addBudget(@RequestBody Map<String, Object> body) {
        return budgetService.addBudget(body);
    }
    @PostMapping("/delete-budget/{slug}")
    public ResponseEntity<String> deleteBudget(@PathVariable String slug) {
        return budgetService.deleteBudget(slug);
    }

    @GetMapping("/budget/{userId}")
    public List<Map<String, Object>> viewBudget(@PathVariable int userId){
        System.out.println("userId: " + userId);
        return budgetService.viewBudget(userId);
    }
    @PostMapping("/edit-budget")
    public ResponseEntity<String> editBudget(@RequestBody Map<String, Object> body){
        return budgetService.editBudget(body);
    }

    @GetMapping("/view-budget/{slug}")
    public Map<String, Object> viewSingleBudget(@PathVariable String slug){
        return budgetService.viewSingleBudget(slug);
    }

    @PostMapping("/update-budget-amount")
    public Map<String, Object> updateBudgetAmount(@RequestBody Map<String, Object> body){
        System.out.println("body: " + body);
        return budgetService.updateBudgetAmount(body);
    }

    @GetMapping("/view-single-budget-track/{budgetId}")
    public List<Map<String,Object>> viewSingleBudgetTrack(@PathVariable int budgetId){
        System.out.println(budgetId);
        return budgetService.viewSingleBudgetTrack(budgetId);
    }

}
