package com.example.fit_friends.service;
import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.domain.Tag;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.AddPostRequest;
import com.example.fit_friends.repository.MatchRepository;
import com.example.fit_friends.repository.PostRepository;
import com.example.fit_friends.repository.TagRepository;
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
    private final TagRepository tagRepository;

    private final MatchRepository matchRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }


    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Long save(AddPostRequest dto) {
        User user = userRepository.findByEmail(dto.getUserEmail()).orElseThrow(null);

        Tag tag = dto.tagToEntity();
        Tag savedTag = tagRepository.save(tag);

        Match match = dto.matchToEntity(user);
        Match savedMatch = matchRepository.save(match);

        Post post = dto.postToEntity(savedTag,savedMatch,user);
        postRepository.save(post);

        return post.getPostId();
    }
}
