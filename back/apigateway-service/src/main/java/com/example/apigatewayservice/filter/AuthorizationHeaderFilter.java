package com.example.apigatewayservice.filter;


import com.example.apigatewayservice.common.dto.CustomResponseBody;
import com.example.apigatewayservice.common.dto.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Base64;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String jwt = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(!isJwtValid(jwt)){
                return onError(exchange, "Jwt token is not valid.", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {
        boolean isValid = true;

        String subject =null;

        try {
            subject = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(env.getProperty("token.secret").getBytes()))
                    .build()
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        }catch (Exception e){
            log.error(e.getMessage());
            isValid = false;
        }

        log.debug("subject: {}", subject);
        if(subject == null || subject.isEmpty()){
            isValid = false;
        }
        return isValid;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        // 커스텀 응답 객체 생성
        CustomResponseBody<Object> response = new CustomResponseBody<>(StatusCode.FAIL, err, null);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(response);

            byte[] bytes = json.getBytes();
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
