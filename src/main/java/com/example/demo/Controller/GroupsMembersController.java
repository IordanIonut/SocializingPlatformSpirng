package com.example.demo.Controller;

import com.example.demo.Model.GroupsMembers;
import com.example.demo.Service.GroupsMembersService;
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
@RequestMapping("/api/groups/members")
public class GroupsMembersController {
    @Autowired
    private GroupsMembersService groupsMembersService;
    @PostMapping("/add")
    public ResponseEntity<Object> saveGroupMembers(@Valid @RequestBody GroupsMembers groupsMembers, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(groupsMembersService.saveGroupsMembers(groupsMembers), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            }
        }
    }
    @GetMapping
    public ResponseEntity<List<GroupsMembers>> findAllGroupsMembers(){
        return ResponseEntity.ok(groupsMembersService.findAllGroupsMembers());
    }
    @GetMapping("/id")
    public ResponseEntity<List<GroupsMembers>> findgroupsMembersById(@RequestParam Long id){
        return groupsMembersService.findGroupsMembersById(id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletegroupsMembers(@PathVariable Long id){
        groupsMembersService.deleteGroupsMembers(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GroupsMembers> updategroupsMembers(@PathVariable Long id, @RequestBody GroupsMembers groupsMember){
        GroupsMembers groupsMembers = groupsMembersService.updateGroupsMembers(id, groupsMember);
        return groupsMembers != null ? ResponseEntity.ok(groupsMembers) : ResponseEntity.noContent().build();
    }
}
