package com.example.demo.Service;

import com.example.demo.Model.Users;
import com.example.demo.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Users saveUsers(Users users) {
        return usersRepository.save(users);
    }

    public Optional<Users> findUsersById(Long id) {
        return usersRepository.findById(id);
    }

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public void deleteUsers(Long id) {
        usersRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        usersRepository.deleteAll();
    }
    public Users updateUsers(Long id, Users users) {
        if (usersRepository.existsById(id)) {
            users.setId_user(id);
            return usersRepository.save(users);
        } else
            return null;
    }

}
