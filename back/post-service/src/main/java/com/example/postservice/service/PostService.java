package com.example.postservice.service;

import com.example.postservice.client.MatchServiceClient;
import com.example.postservice.client.UserServiceClient;
import com.example.postservice.client.dto.MatchResponse;
import com.example.postservice.client.dto.SaveMatchRequest;
import com.example.postservice.client.dto.UserResponse;
import com.example.postservice.common.exception.PostNotFoundException;
import com.example.postservice.domain.Post;
import com.example.postservice.domain.Tag;
import com.example.postservice.dto.*;
import com.example.postservice.repository.PostRepository;
import com.example.postservice.repository.TagRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final MatchServiceClient matchServiceClient;
    private final UserServiceClient userServiceClient;

    public List<Post> findByCategory(String category) { return postRepository.findByCategory(category);}

    public List<PostResponse> findPosts(String category, String levelType, String ageType, char genderType){
        List<Post> posts = postRepository.findPosts(category, levelType, ageType, genderType);

        return posts.stream().map(post -> PostResponse.builder()
                .title(post.getTitle())
                .category(post.getCategory())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .tag(post.getTag())
                .userName(userServiceClient.getUser(post.getUserId()).getName())
                .userImage(userServiceClient.getUser(post.getUserId()).getPicture())
                .match(matchServiceClient.getMatch(post.getMatchId()))
                .build())
                .collect(Collectors.toList());
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        UserResponse user = userServiceClient.getUser(post.getUserId());
        MatchResponse match = matchServiceClient.getMatch(post.getMatchId());

        return PostResponse.builder()
                .title(post.getTitle())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .category(post.getCategory())
                .tag(post.getTag())
                .userName(user.getName())
                .userImage(user.getPicture())
                .match(match)
                .build();
    }

    @Transactional
    public PostIdResponse save(AddPostRequest dto, UUID userId) {
        Tag tag = dto.tagToEntity();
        Tag savedTag = tagRepository.save(tag);
        Long matchId = matchServiceClient.saveMatch(
                new SaveMatchRequest(
                        userId, dto.getCategory(), dto.getMatch().getPlace(), dto.getMatch().getStartTime(), dto.getMatch().getEndTime(), dto.getMatch().getHeadCnt()
                )
        );

        Post post = dto.postToEntity(savedTag,matchId,userId);
        Post saved = postRepository.save(post);

        return new PostIdResponse(saved.getPostId());
    }

    @Transactional
    public void deleteById(UUID userId , Long id){
        if ((postRepository.findById(id).orElseThrow(PostNotFoundException::new)).getUserId().equals(userId)) {
            postRepository.deleteById(id);
        }
    }

    @Transactional
    public PostIdResponse updatePost(UUID userId, Long postId, UpdatePostRequest dto){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        if (post.getUserId().equals(userId)){
            Tag tag = tagRepository.findById(post.getTag().getTagId()).orElseThrow(NotFoundException::new);
            matchServiceClient.updateMatch(dto.getMatch(), post.getMatchId());
            post.setTitle(dto.getTitle());
            post.setContent(dto.getContent());
            tag.setAgeType(dto.getTag().getAgeType());
            tag.setGenderType(dto.getTag().getGenderType());
            tag.setLevelType(dto.getTag().getLevelType());

            return new PostIdResponse(post.getPostId());
        }else {
            throw new IllegalArgumentException();
        }
    }


    public TagByPostResponse findTagByPost(Long matchId) {
        Post post = postRepository.findOneByMatchId(matchId).orElseThrow(PostNotFoundException::new);
        Tag tag = post.getTag();

        return new TagByPostResponse(matchId,
                tag.getTagId(),
                tag.getGenderType(),
                tag.getLevelType(),
                tag.getAgeType());

    }
}
