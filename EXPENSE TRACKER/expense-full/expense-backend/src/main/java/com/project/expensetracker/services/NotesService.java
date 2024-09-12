package com.project.expensetracker.services;

import com.project.expensetracker.repo.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class NotesService {
    @Autowired
    NotesRepository notesRepository;
    public ResponseEntity<String> addNote(@RequestBody Map<String,String> body)
    {
        int userId =Integer.parseInt( body.get("user_id"));
        String noteTitle = body.get("note_title");
        String noteDescription = body.get("note_description");

        int noOfRowsAffected =  notesRepository.addNote(userId,noteTitle,noteDescription);
        if (noOfRowsAffected > 0)
        {
            return ResponseEntity.ok("Note is added");
        }
        else
        {
            return ResponseEntity.badRequest().body("Note is not added");
        }

    }
    public Map<String, Object> getNote (int note_id, int user_id)
    {
        return notesRepository.getNote(note_id,user_id);
    } public List<Map<String, Object>> getNotes(int userId)
    {
        return notesRepository.getNotes(userId);
    }
}
