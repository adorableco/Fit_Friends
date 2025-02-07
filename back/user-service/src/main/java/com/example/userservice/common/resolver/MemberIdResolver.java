package com.example.userservice.common.resolver;


import com.example.userservice.common.auth.JwtAuthProvider;
import com.example.userservice.common.resolver.memberid.MemberId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private JwtAuthProvider jwtAuthProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        MemberId annotation = parameter.getParameterAnnotation(MemberId.class);



        return annotation != null && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String authorization = request.getHeader("Authorization");

        if(!jwtAuthProvider.validateToken(authorization)) {
            throw new AuthorizationDeniedException("Invalid token");
        }

        return jwtAuthProvider.getIdByToken(authorization);
    }
}
