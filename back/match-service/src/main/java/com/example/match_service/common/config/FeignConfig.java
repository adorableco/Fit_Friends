package com.example.match_service.common.config;

import com.example.match_service.common.dto.CustomResponseBody;
import com.fasterxml.jackson.databind.type.TypeFactory;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                requestTemplate.header("Authorization", request.getHeader("Authorization"));
            }
        };
    }

    @Bean
    public Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new FeignDecoder(new SpringDecoder(messageConverters));
    }

    public static class FeignDecoder implements Decoder {
        private final Decoder decoder;

        public FeignDecoder(Decoder decoder) {
            this.decoder = decoder;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            var returnType = TypeFactory.rawClass(type);
            var forClassWithGenerics =
                    ResolvableType.forClassWithGenerics(CustomResponseBody.class, returnType);

            try {
                return ((CustomResponseBody<?>) decoder.decode(response,
                        forClassWithGenerics.getType())).getData();
            } catch (Exception e) {
                return decoder.decode(response, forClassWithGenerics.getType());
            }
        }
    }
}
