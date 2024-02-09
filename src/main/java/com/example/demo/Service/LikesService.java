package com.example.demo.Service;

import com.example.demo.Model.Likes;
import com.example.demo.Model.Users;
import com.example.demo.Repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikesService {
    @Autowired
    private LikesRepository likesRepository;

    public Likes createLikes(Likes likes) {
        return likesRepository.save(likes);
    }

    public Optional<Likes> findLikesById(Long id) {
        return likesRepository.findById(id);
    }

    public List<Likes> findAllLikes() {
        return likesRepository.findAll();
    }

    public void deleteLikes(Long id) {
        likesRepository.deleteById(id);
    }

    public Likes updateLikes(Long id, Likes likes) {
        if (likesRepository.existsById(id)) {
            likes.setId_like(id);
            return likesRepository.save(likes);
        } else return null;
    }
}
