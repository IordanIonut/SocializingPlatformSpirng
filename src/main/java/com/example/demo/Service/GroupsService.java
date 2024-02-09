package com.example.demo.Service;
import com.example.demo.Model.Groups;
import com.example.demo.Repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupsService {
    @Autowired
    private GroupsRepository groupsRepository;
    public Groups saveGroups(Groups groups) {
        return groupsRepository.save(groups);
    }
    public Optional<Groups> findGroupsById(Long id){
        return groupsRepository.findById(id);
    }
    public List<Groups> findAllGroups(){
        return groupsRepository.findAll();
    }
    public void deleteGroups(Long id){
        groupsRepository.deleteById(id);
    }
    public Groups updateGroups(Long id, Groups groups){
        if(groupsRepository.existsById(id)){
            groups.setId_group(id);
            return groupsRepository.save(groups);
        }else return null;
    }
}
