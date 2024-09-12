package com.project.expensetracker.web;

import com.project.expensetracker.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class NotesResource {
    @Autowired
    NotesService notesService;
    @PostMapping("/note")
    public ResponseEntity<String> addNote(@RequestBody Map<String,String> body)
    {
        return notesService.addNote(body);}
    @GetMapping("/notes/{userId}")
    public List<Map<String,Object>> getNotes(@PathVariable int userId)
    {
        return notesService.getNotes(userId);
    }

    @GetMapping("/note/{user_id}/{note_id}")
    public Map<String, Object> getNote(@PathVariable String user_id , @PathVariable String note_id) {
        return notesService.getNote(Integer.parseInt(note_id),Integer.parseInt(user_id));}

}
