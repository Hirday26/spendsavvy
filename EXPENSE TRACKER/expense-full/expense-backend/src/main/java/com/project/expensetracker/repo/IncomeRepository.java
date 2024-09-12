package com.project.expensetracker.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class IncomeRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

public int addIncome(int userId, int incomeAmount, int incomeType) {
        return jdbcTemplate.update("EXEC add_user_income ?, ?, ?", userId, incomeAmount, incomeType);
    }
    public List<Map<String,Object>> getIncome(int userId)
    {
        return jdbcTemplate.queryForList("EXEC view_user_income ?",userId);
    }
    public Map<String , Object> getTotalMonthlyIncome(int userId)
    {
        return jdbcTemplate.queryForMap("EXEC view_user_income_sum_current_month ?",userId);
    }
}
