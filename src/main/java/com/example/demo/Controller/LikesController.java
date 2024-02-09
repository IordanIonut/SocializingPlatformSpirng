package com.example.demo.Controller;

import com.example.demo.Model.Likes;
import com.example.demo.Service.LikesService;
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
@RequestMapping("/api/likes")
public class LikesController {
    @Autowired
    private LikesService likesService;

    @PostMapping("/add")
    public ResponseEntity<Object> createLikes(@Valid @RequestBody Likes likes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(likesService.createLikes(likes), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Likes>> findAllLikes() {
        return ResponseEntity.ok(likesService.findAllLikes());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Likes>> findLikesById(@RequestParam Long id) {
        return likesService.findLikesById(id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLikes(@PathVariable("id") Long id) {
        likesService.deleteLikes(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Likes> updateLikes(@PathVariable Long id, @RequestBody Likes like) {
        Likes likes = likesService.updateLikes(id, like);
        return likes != null ? ResponseEntity.ok(likes) : ResponseEntity.notFound().build();
    }
}
