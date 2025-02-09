package com.example.userservice.controller;

import com.example.userservice.common.dto.CustomResponseBody;
import com.example.userservice.common.resolver.userid.UserId;
import com.example.userservice.common.util.ResponseUtil;
import com.example.userservice.domain.User;
import com.example.userservice.dto.LoadUserDetailResponse;
import com.example.userservice.dto.ModifyUserDetailRequest;
import com.example.userservice.dto.SaveUserRequest;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SaveUserRequest request) {
        User save = userService.save(request);
        try {
            return ResponseEntity.ok()
                    .body(save);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body(save);
        }
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<CustomResponseBody<MappingJacksonValue>> loadUserDetail(@PathVariable UUID userId, @UserId String memberId) {

        LoadUserDetailResponse user = userService.findUser(userId, UUID.fromString(memberId));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("matchId","category","headCnt","place","tag","startTime","endTime","attendanceCnt");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return ResponseUtil.success(mapping);
    }

    @PutMapping("/user")
    String modifyUserDetail(@UserId UUID memberId , @RequestBody ModifyUserDetailRequest request) {
        return userService.modifyUserDetail(request, memberId);

    }
}
