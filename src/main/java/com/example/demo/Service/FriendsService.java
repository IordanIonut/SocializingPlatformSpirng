package com.example.demo.Service;
import com.example.demo.Model.Friends;
import com.example.demo.Repository.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendsService {
    @Autowired
    private FriendsRepository friendsRepository;
    public Friends saveFriends(Friends friends){
        return friendsRepository.save(friends);
    }
    public Optional<Friends> findFriendsById(Long id){
        return friendsRepository.findById(id);
    }
    public List<Friends> findAllFriends(){
        return friendsRepository.findAll();
    }
    public void deleteFriends(Long id){
        friendsRepository.deleteById(id);
    }
    public Friends updateFriends(Long id, Friends friends){
        if(friendsRepository.existsById(id)){
            friends.setId_friend(id);
            return friendsRepository.save(friends);
        }else return null;
    }
}
