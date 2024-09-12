package com.project.expensetracker.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ExpenseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> getCategories() {
        return jdbcTemplate.queryForList("EXEC sp_fetch_categories");
    }
//   add expense
    public int addExpense(String expenseName,int expenseAmount,String expenseDescription,String expenseDate,int categoryNameID , String tags , int userId)
    {
        return jdbcTemplate.update("EXEC sp_add_expense ?,?,?,?,? , ? , ?",expenseName,expenseAmount,expenseDescription,expenseDate,categoryNameID , tags , userId);
    }
    public List<Map<String,Object>> getExpenses(int userId)
    {
        return jdbcTemplate.queryForList("EXEC sp_fetch_expenses ?",userId);
    }
    public List<Map<String,Object>> getExpensesCategoryWise(int userId)
    {
        return jdbcTemplate.queryForList("EXEC sp_fetch_expenses_categorywise ?",userId);
    }
    public List<Map<String,Object>> getPhoneNumber()
    {
        return jdbcTemplate.queryForList("EXEC sp_get_phone_number");
    }

    public Map<String, Object> getTotalMonthlyExpense(int userId) {
        return jdbcTemplate.queryForMap("EXEC sp_fetch_expenses_month_wise ?", userId);
    }


}
