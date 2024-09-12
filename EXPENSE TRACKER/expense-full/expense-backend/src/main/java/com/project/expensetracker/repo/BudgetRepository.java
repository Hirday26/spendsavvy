
package com.project.expensetracker.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BudgetRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public int addBudget( String budgetName,int userId, String budgetDescription, int targetAmount, String dateToAchieve, int categoryId , String tags) {
        return jdbcTemplate.update("EXEC [dbo].[add_budget] ?, ?, ?, ?, ?, ? , ?",  budgetName,userId, budgetDescription, targetAmount, dateToAchieve, categoryId,tags);
    }

    public int deleteBudget(String slug) {
        return jdbcTemplate.update("EXEC [dbo].[soft_delete_budget] ?", slug);
    }

    public List<Map<String, Object>> viewBudget(int userId){
        return jdbcTemplate.queryForList("EXEC dbo.view_budget ?",userId);
    }

    public Map<String, Object> viewSingleBudget(String slug){
        return jdbcTemplate.queryForMap("EXEC dbo.view_single_budget ?",slug);
    }

    public int editBudget( String budgetName,String slug, String budgetDescription, int targetAmount, String dateToAchieve, int categoryId) {
        return jdbcTemplate.update("EXEC [dbo].[edit_budget] ?, ?, ?, ?, ?, ?",  budgetName, budgetDescription, targetAmount, dateToAchieve, categoryId,slug);
    }
    public int updateBudgetAmount(String slug, int targetAmount) {
        return jdbcTemplate.update("EXEC [dbo].[update_budget_amount] ?, ?", targetAmount, slug);
    }
    public List<Map<String,Object>> viewSingleBudgetTrack(int budgetId){
        return jdbcTemplate.queryForList("EXEC dbo.view_single_budget_track ?",budgetId);
    }

}