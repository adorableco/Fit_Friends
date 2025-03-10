package com.example.userservice.common.resolver;

import com.example.userservice.common.auth.JwtAuthProvider;
import com.example.userservice.common.resolver.userid.UserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;


@Slf4j
@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private JwtAuthProvider jwtAuthProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        UserId annotation = parameter.getParameterAnnotation(UserId.class);
        return annotation != null && parameter.getParameterType().equals(UUID.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorization = request.getHeader("Authorization");
        return jwtAuthProvider.getIdByToken(authorization);
    }
}
