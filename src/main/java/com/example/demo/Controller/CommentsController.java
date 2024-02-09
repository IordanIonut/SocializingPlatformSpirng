package com.example.demo.Controller;

import com.example.demo.Model.Comments;
import com.example.demo.Service.CommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;
    @PostMapping("/add")
    public ResponseEntity<Object> saveComments(@Valid @RequestBody Comments comments, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(commentsService.saveComments(comments), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            }
        }
    }
    @GetMapping
    public ResponseEntity<List<Comments>> findAllComments(){
        return ResponseEntity.ok(commentsService.findAllComments());
    }
    @GetMapping("/id")
    public ResponseEntity<List<Comments>> findComments(@RequestParam Long id){
        return commentsService.findCommentsById(id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComments(@PathVariable Long id){
        commentsService.deleteComments(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Comments> updateComments(@PathVariable Long id, @RequestBody Comments comment){
        Comments comments = commentsService.updateComments(id, comment);
        return comments != null ? ResponseEntity.ok(comments) : ResponseEntity.notFound().build();
    }
}
