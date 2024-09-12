package com.project.expensetracker.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public int registerUser(String fullname,String username,String email, String password, String mobile , String dob , int incomeType)
    {
        return jdbcTemplate.update("EXEC sp_register_user ?,?,?,?,?,?,?",username,fullname,password,incomeType,dob,email,mobile);
    }
    public List<Map<String,Object>> getEmails()
    {
        return jdbcTemplate.queryForList("EXEC sp_get_mails");
    }


    public Map<String, Object> loginUser(String username, String password) {
        System.out.println(jdbcTemplate.queryForMap("EXEC sp_login_user ? , ? ", username, password));
        return jdbcTemplate.queryForMap("EXEC sp_login_user ? , ? ", username, password);
    }
    public Map<String, Object> isValidToken(Integer userid, String token) {
        return jdbcTemplate.queryForMap("EXEC sp_validate_token ? , ? ", userid, token);
    }
    public List<Map<String , Object>> getIncomeTypes()
    {
        return jdbcTemplate.queryForList("EXEC sp_fetch_income");
    }









}
