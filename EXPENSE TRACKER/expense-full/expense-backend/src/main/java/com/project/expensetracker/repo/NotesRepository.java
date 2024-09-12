package com.project.expensetracker.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.PSource;
import java.util.List;
import java.util.Map;

@Repository
public class NotesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public int addNote(int userId,String noteTitle, String noteDescription)
    {
        return jdbcTemplate.update("EXEC  sp_add_note ?,?,?",userId,noteTitle,noteDescription);
    }
    public Map<String, Object> getNote(int note_id, int user_id)
    {
        System.out.println(jdbcTemplate.queryForMap("EXEC sp_get_notes ?,?",user_id,note_id));
        return jdbcTemplate.queryForMap("EXEC sp_get_notes ?,?",user_id,note_id);
    }
    public List<Map<String, Object>> getNotes(int userId)
    {
        return jdbcTemplate.queryForList("EXEC sp_get_all_notes ?",userId);
    }
}
