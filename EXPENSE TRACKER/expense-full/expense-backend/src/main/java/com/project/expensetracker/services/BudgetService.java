
package com.project.expensetracker.services;

import com.project.expensetracker.repo.BudgetRepository;
import com.project.expensetracker.web.BudgetResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepo;

    public ResponseEntity<Map<String ,Object>> addBudget(Map<String, Object> body) {
        Integer userId = Integer.parseInt((String) body.get("userId"));
        String budgetName = (String)body.get("budgetName");
        String budgetDescription = (String)body.get("budgetDescription");
        int targetAmount =(int)(body.get("targetAmount"));
        String dateToAchieve = (String)body.get("dateToAchieve");
        String tags = (String)body.get("tags");
        int categoryId = Integer.parseInt((String) body.get("categoryId"));

        int insertedRows = budgetRepo.addBudget( budgetName,userId, budgetDescription, targetAmount, dateToAchieve, categoryId,tags);
        if (insertedRows > 0) {
            return ResponseEntity.ok(Map.of("status","success adding budget"));
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status","error adding budget"));
    }
    public ResponseEntity<String> editBudget(Map<String, Object> body){
        String budgetName = (String)body.get("budgetName");
        String budgetDescription = (String)body.get("budgetDescription");
        int targetAmount =(int)(body.get("targetAmount"));
        String dateToAchieve = (String)body.get("dateToAchieve");

        int categoryId = (int)(body.get("categoryId"));
        String slug=(String) (body.get("slug"));
        int insertedRows = budgetRepo.editBudget( budgetName,slug,budgetDescription, targetAmount, dateToAchieve, categoryId );
        if (insertedRows > 0) {
            return ResponseEntity.ok("ok");
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not ok");
    }
    public ResponseEntity<String> deleteBudget(String slug) {
        int noOfRows=budgetRepo.deleteBudget(slug);
        if (noOfRows > 0) {
            return ResponseEntity.ok("ok");
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not ok");
    }
    public List<Map<String, Object>> viewBudget(int userId){
        return budgetRepo.viewBudget(userId);
    }

    public Map<String, Object> viewSingleBudget(String slug){
        return budgetRepo.viewSingleBudget(slug);
    }

    public Map<String,Object> updateBudgetAmount(Map<String, Object> body){
        int amount =(int) (body.get("amount"));

        String slug=(String) (body.get("slug"));
        int insertedRows = budgetRepo.updateBudgetAmount(slug, amount);
        if (insertedRows > 0) {
            return Map.of("status","success");
        }

        return Map.of("status","error");
    }
    public List<Map<String,Object>> viewSingleBudgetTrack(int budgetId){
        return budgetRepo.viewSingleBudgetTrack(budgetId);
    }
}
