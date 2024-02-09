package com.example.demo.Controller;

import com.example.demo.Model.Posts;
import com.example.demo.Model.Users;
import com.example.demo.Service.PostsService;
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
@RequestMapping("/api/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;

    @PostMapping("/add")
    public ResponseEntity<Object> savePost(@Valid @RequestBody Posts post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(postsService.savePosts(post), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Posts>> findAllPosts() {
        return ResponseEntity.ok(postsService.findAllPosts());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Posts>> findPostsById(@RequestParam Long id) {
        return postsService.findPostsById(id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePosts(@PathVariable("id") Long id) {
        postsService.deletePosts(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Posts> updatePosts(@PathVariable Long id, @RequestBody Posts post) {
        Posts posts = postsService.updatePosts(id, post);
        return posts != null ? ResponseEntity.ok(posts) : ResponseEntity.notFound().build();
    }
}
