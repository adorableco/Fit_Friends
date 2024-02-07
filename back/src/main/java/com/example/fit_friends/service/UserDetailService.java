package com.example.fit_friends.service;

import com.example.fit_friends.config.auth.JwtAuthProvider;
import com.example.fit_friends.domain.Participation;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.LoadUserDetailResponse;
import com.example.fit_friends.dto.ModifyUserDetailRequest;
import com.example.fit_friends.repository.ParticipationRepository;
import com.example.fit_friends.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    @Autowired
    private final JwtAuthProvider jwtAuthProvider;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ParticipationService participationService;

    @Autowired
    private final ParticipationRepository participationRepository;

    @Transactional
    public LoadUserDetailResponse findUser(String token, Long userId) {
        User viewer = userRepository.findByEmail(jwtAuthProvider.getEmailbyToken(token)).get();
        User user = userRepository.findById(userId).get();
        List<Participation> participationList = participationService.findByUser(user);
        LoadUserDetailResponse response = LoadUserDetailResponse.builder()
                .name(user.getName())
                .picture(user.getPicture())
                .level(user.getLevel())
                .attendanceRate(user.getAttendanceRate())
                .winningRate(user.getWinningRate())
                .participationList(participationList)
                .build();

        Float attendanceRate = (participationService.countMyPresentedMatches(user.getUserId()) / participationService.countMyEndMatches(LocalDateTime.now(), user.getUserId()) ) * 100;
        response.setAttendanceRate(attendanceRate);

        if (viewer.getUserId() != user.getUserId()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                    .filterOutAllExcept("gender","role","age","gender");

            FilterProvider filters = new SimpleFilterProvider().addFilter("User",filter);
            MappingJacksonValue mapping = new MappingJacksonValue(user);
        }



        if (viewer.getUserId() == user.getUserId()) {
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

