package com.example.demo.Controller;

import com.example.demo.Model.Groups;
import com.example.demo.Service.GroupsService;
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
@RequestMapping("/api/groups")
public class GroupsController {
    @Autowired
    private GroupsService groupsService;

    @PostMapping("/add")
    public ResponseEntity<Object> saveGroups(@Valid @RequestBody Groups groups, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(groupsService.saveGroups(groups), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Groups>> findAllGroups() {
        return ResponseEntity.ok(groupsService.findAllGroups());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Groups>> findGroupsById(@RequestParam Long id) {
        return groupsService.findGroupsById(id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroups(@PathVariable Long id) {
        groupsService.deleteGroups(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Groups> updateGroups(@PathVariable Long id, @RequestBody Groups group) {
        Groups groups = groupsService.updateGroups(id, group);
        return groups != null ? ResponseEntity.ok(groups) : ResponseEntity.notFound().build();
    }
}
