package com.example.demo.Service;

import com.example.demo.Model.Comments;
import com.example.demo.Repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    public Comments saveComments(Comments comments){
        return commentsRepository.save(comments);
    }
    public Optional<Comments> findCommentsById(Long id){
        return commentsRepository.findById(id);
    }
    public List<Comments> findAllComments(){
        return commentsRepository.findAll();
    }
    public void deleteComments(Long id){
        commentsRepository.deleteById(id);
    }
    public Comments updateComments(Long id, Comments comments){
        if(commentsRepository.existsById(id)){
            comments.setId_comment(id);
            return commentsRepository.save(comments);
        }return null;
    }
}
