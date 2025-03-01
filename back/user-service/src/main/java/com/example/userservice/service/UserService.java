package com.example.userservice.service;

import com.example.userservice.client.MatchServiceClient;
import com.example.userservice.client.dto.ParticipationResponse;
import com.example.userservice.common.exception.UserNotFoundException;
import com.example.userservice.domain.User;
import com.example.userservice.dto.*;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final MatchServiceClient matchServiceClient;

    @Transactional
    public User save(SaveUserRequest saveUserRequest) {
        return userRepository.save(saveUserRequest.toEntity());
    }

    public LoadUserDetailResponse findUser(UUID userId, UUID me){
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<ParticipationResponse> participationList = matchServiceClient.getParticipationList(userId);
        LoadUserDetailResponse response = LoadUserDetailResponse.builder()
                .name(user.getName())
                .picture(user.getPicture())
                .level(user.getLevel())
                .attendanceRate(user.getAttendanceRate())
                .winningRate(user.getWinningRate())
                .participationList(participationList)
                .build();

        if (userId.equals(me)) {
            response.setAge(user.getAge());
            response.setGender(user.getGender());
            response.setIsMyDetail(Boolean.TRUE);
            return response;
        }else{
            response.setIsMyDetail(Boolean.FALSE);

        }

        if (user.isAgeVisible()) {
            response.setAge(user.getAge());
        }
        if (user.isGenderVisible()) {
            response.setGender(user.getGender());
        }

        return response;
    }

    @Transactional
    public String modifyUserDetail(ModifyUserDetailRequest request, UUID userId) {
        try{
            User user = userRepository.findByUserId(userId).get();
            user.setName(request.getName());
            user.setAgeVisible(request.isAgeVisible());
            user.setGenderVisible(request.isGenderVisible());

            return "회원 정보 수정 성공";
        }catch(Exception e) {
            return "회원 정보 수정 오류";
        }

    }

    @Transactional
    public void applyGameResult(ApplyGameResultRequest request) {
        request.getGameresults()
                .forEach(game -> {
                    User user = userRepository.findByUserId(game.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + game.getUserId()));
                    user.updateWinningRate(game.getResult());
                });
    }
}
