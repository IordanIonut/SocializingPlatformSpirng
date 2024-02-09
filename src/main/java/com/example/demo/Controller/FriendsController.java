package com.example.demo.Controller;

import com.example.demo.Model.Friends;
import com.example.demo.Service.FriendsService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
@RequestMapping("/api/friens")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;
    @PostMapping("/add")
    public ResponseEntity<Object> saveFriends(@Valid @RequestBody Friends friends, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(friendsService.saveFriends(friends), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            }
        }
    }
    @GetMapping
    public ResponseEntity<List<Friends>> findAllFriends(){
        return ResponseEntity.ok(friendsService.findAllFriends());
    }
    @GetMapping("/id")
    public ResponseEntity<List<Friends>> findFriendsById(@RequestParam Long id){
        return friendsService.findFriendsById(id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFriends(@PathVariable Long id){
        friendsService.deleteFriends(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Friends> updateFriends(@PathVariable Long id, @RequestBody Friends friend){
        Friends friends = friendsService.updateFriends(id,friend);
        return friends != null ? ResponseEntity.ok(friends) : ResponseEntity.notFound().build();
    }
}
