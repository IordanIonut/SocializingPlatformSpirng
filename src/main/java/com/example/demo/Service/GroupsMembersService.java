package com.example.demo.Service;
import com.example.demo.Model.GroupsMembers;
import com.example.demo.Repository.GroupsMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsMembersService {
    @Autowired
    private GroupsMembersRepository groupsMembersRepository;
    public GroupsMembers saveGroupsMembers(GroupsMembers groupsMembers){
        return groupsMembersRepository.save(groupsMembers);
    }
    public Optional<GroupsMembers> findGroupsMembersById(Long id){
        return groupsMembersRepository.findById(id);
    }
    public List<GroupsMembers> findAllGroupsMembers(){
        return groupsMembersRepository.findAll();
    }
    public void deleteGroupsMembers(Long id){
        groupsMembersRepository.deleteById(id);
    }
    public GroupsMembers updateGroupsMembers(Long id, GroupsMembers groupsMembers){
        if(groupsMembersRepository.existsById(id)){
            groupsMembers.setId_group_member(id);
            return groupsMembersRepository.save(groupsMembers);
        }else return null;
    }
}
