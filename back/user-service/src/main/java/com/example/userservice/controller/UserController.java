package com.example.userservice.controller;

import com.example.userservice.common.dto.CustomResponseBody;
import com.example.userservice.common.util.ResponseUtil;
import com.example.userservice.dto.LoadUserDetailResponse;
import com.example.userservice.dto.ModifyUserDetailRequest;
import com.example.userservice.service.UserDetailService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class UserController {

    private final UserDetailService userDetailService;

    @Autowired
    public UserController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/api/user/{userId}")
    ResponseEntity<CustomResponseBody<MappingJacksonValue>> loadUserDetail(HttpServletRequest header, @PathVariable Long userId) {
        String token = header.getHeader("Authorization");
        LoadUserDetailResponse user = userDetailService.findUser(token, userId);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("matchId","category","headCnt","place","tag","startTime","endTime","attendanceCnt");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return ResponseUtil.success(mapping);
    }

    @PutMapping("/api/user")
    String modifyUserDetail(HttpServletRequest header, @RequestBody ModifyUserDetailRequest request) {
        String token = header.getHeader("Authorization");
        return userDetailService.modifyUserDetail(request, token);

    }
}
