package com.example.fit_friends.service;

import com.example.fit_friends.domain.Post;
import com.example.fit_friends.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
