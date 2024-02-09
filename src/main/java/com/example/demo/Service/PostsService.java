package com.example.demo.Service;

import com.example.demo.Model.Posts;
import com.example.demo.Repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;
    public Posts savePosts(Posts posts) {
        return postsRepository.save(posts);
    }
    public Optional<Posts> findPostsById(Long id) {
        return postsRepository.findById(id);
    }
    public List<Posts> findAllPosts() {
        return postsRepository.findAll();
    }
    public void deletePosts(Long id) {
        postsRepository.deleteById(id);
    }
    public Posts updatePosts(Long id, Posts posts) {
        if (postsRepository.existsById(id)) {
            posts.setId_post(id);
            return postsRepository.save(posts);
        } else
            return null;
    }
}
