package com.example.userservice.service;

import com.example.userservice.auth.JwtAuthProvider;
import com.example.userservice.client.MatchServiceClient;
import com.example.userservice.domain.User;
import com.example.userservice.dto.LoadUserDetailResponse;
import com.example.userservice.dto.ModifyUserDetailRequest;
import com.example.userservice.dto.ParticipationResponse;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    @Autowired
    private final JwtAuthProvider jwtAuthProvider;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MatchServiceClient matchServiceClient;

    @Transactional
    public LoadUserDetailResponse findUser(String token, Long userId) {
        User viewer = userRepository.findByEmail(jwtAuthProvider.getEmailbyToken(token)).get();
        User user = userRepository.findById(userId).get();
        List<ParticipationResponse> participationList = matchServiceClient.getParticipationList(userId);
        LoadUserDetailResponse response = LoadUserDetailResponse.builder()
                .name(user.getName())
                .picture(user.getPicture())
                .level(user.getLevel())
                .attendanceRate(user.getAttendanceRate())
                .winningRate(user.getWinningRate())
                .participationList(participationList)
                .build();

        double attendanceRate = matchServiceClient.getAttendanceRate(userId);
        response.setAttendanceRate(attendanceRate);


        if (viewer.getUserId().equals(user.getUserId())) {
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
    public String modifyUserDetail(ModifyUserDetailRequest request, String token) {
        try{
            User user = userRepository.findByEmail(jwtAuthProvider.getEmailbyToken(token)).get();
            user.setName(request.getName());
            user.setAgeVisible(request.isAgeVisible());
            user.setGenderVisible(request.isGenderVisible());

            return "회원 정보 수정 성공";
        }catch(Exception e) {
            return "회원 정보 수정 오류";
        }

    }

}
