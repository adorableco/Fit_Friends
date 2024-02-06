package com.example.fit_friends.controller;

import com.example.fit_friends.dto.LoadUserDetailResponse;
import com.example.fit_friends.service.UserDetailService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailController {

    private final UserDetailService userDetailService;

    @Autowired
    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/api/user/{userId}")
    ResponseEntity<MappingJacksonValue> loadUserDetail(HttpServletRequest header, @PathVariable Long userId) {
        String token = header.getHeader("Authorization");
        LoadUserDetailResponse user = userDetailService.findUser(token, userId);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("category","headCnt","place","tag","startTime","endTime","attendanceCnt");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return ResponseEntity.ok()
                .body(mapping);
    }
}
