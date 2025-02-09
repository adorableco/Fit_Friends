package com.example.userservice.common.resolver;


import com.example.userservice.common.resolver.userid.UserId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        UserId annotation = parameter.getParameterAnnotation(UserId.class);
        return annotation != null && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorization = request.getHeader("User-Id");

        if(authorization.isEmpty()) {
            throw new AuthorizationDeniedException("Invalid token");
        }
        return authorization;
    }
}
