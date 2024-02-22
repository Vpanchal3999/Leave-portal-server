package com.synoverge.leave.portal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synoverge.leave.portal.DTO.BaseResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.debug("Entry!! :: commence :: with request => {}", request);
        response.setStatus(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name());
        BaseResponseEntity baseResponse = new BaseResponseEntity(response.getStatus(), HttpStatus.UNAUTHORIZED.name(), "You doesn't have authority to access it..", null);
        final ObjectMapper mapper = new ObjectMapper();
        log.debug("Exit!! :: commence :: exception => {}", authException.getMessage());
        mapper.writeValue(response.getOutputStream(), baseResponse);
    }
}
