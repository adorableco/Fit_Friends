package com.example.fit_friends.service;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.AddPostRequest;
import com.example.fit_friends.repository.PostRepository;
import com.example.fit_friends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }


    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Long save(Match match, String email, String category, AddPostRequest dto) {
        User user = userRepository.findByEmail(email).orElseThrow(null);
        dto.setUser(user);
        dto.setMatch(match);
        dto.setCategory(category);

        Post post = dto.toEntity();
        postRepository.save(post);

        return post.getPostId();
    }
}
