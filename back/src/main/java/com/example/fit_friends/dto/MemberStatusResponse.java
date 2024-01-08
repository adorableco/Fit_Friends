package com.example.fit_friends.dto;

import com.example.fit_friends.domain.Role;
import com.example.fit_friends.domain.User;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class MemberStatusResponse {

    @NonNull
    private String role;
    private String name;
    private String email;
    private String picture;


    @NonNull
    private String accessToken;

    public User toEntity() {
        return User.builder()
                .role(Role.GUEST)
                .name(name)
                .email(email)
                .accessToken(accessToken)
                .build();
    }
}
