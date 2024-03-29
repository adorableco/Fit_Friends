package com.example.fit_friends.service;
import com.example.fit_friends.config.auth.JwtAuthProvider;
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
import org.springframework.transaction.annotation.Transactional;

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

    public List<Post> findByCategory(String category) { return postRepository.findByCategory(category);}


    public List<Post> findPosts(String category, String levelType, String ageType, char genderType){
        return postRepository.findPosts(category, levelType, ageType, genderType);
    }


    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Long save(AddPostRequest dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(null);

        Tag tag = dto.tagToEntity();
        Tag savedTag = tagRepository.save(tag);

        Match match = dto.matchToEntity(savedTag, user);
        Match savedMatch = matchRepository.save(match);

        Post post = dto.postToEntity(savedTag,savedMatch,user);
        postRepository.save(post);

        return post.getPostId();
    }

    public String deleteById(String email , Long id){
        try{
        if ((postRepository.findById(id).get().getUser().getEmail()).equals(email)) {
            postRepository.deleteById(id);
            return "모집글 삭제 완료";
        }else {
            return "모집글을 등록한 회원이 아님";
        }
        }catch (Exception e){
            return "모집글 삭제 실패 " + e;
        }
    }

    @Transactional
    public String updatePost(String email, Long id, AddPostRequest dto){

        Post post = postRepository.findById(id).get();
        if ((postRepository.findById(id).get().getUser().getEmail()).equals(email)){
            Match match = matchRepository.findById(post.getMatch().getMatchId()).get();
            Tag tag = tagRepository.findById(post.getTag().getTagId()).get();

            post.setTitle(dto.getTitle());
            post.setContent(dto.getContent());
            match.setStartTime(dto.getMatch().getStartTime());
            match.setEndTime(dto.getMatch().getStartTime());
            match.setPlace(dto.getMatch().getPlace());
            match.setHeadCnt(dto.getMatch().getHeadCnt());
            tag.setAgeType(dto.getTag().getAgeType());
            tag.setGenderType(dto.getTag().getGenderType());
            tag.setLevelType(dto.getTag().getLevelType());

            return "모집글 수정 완료";
        }else {
            return "모집글을 등록한 회원이 아님";
        }

    }


}
