package com.synoverge.leave.portal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synoverge.leave.portal.DTO.BaseResponseEntity;
import com.synoverge.leave.portal.controller.AdminController;
import com.synoverge.leave.portal.security.JwtUserDetailsService;
import com.synoverge.leave.portal.utility.ErrorMessages;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    private final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, AuthenticationException {

        response.setHeader("Access-Control-Allow-Origin", "http://192.168.2.199:8000");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST, PUT, DELETE, PATCH,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers","Authorization, Cache-Control, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");

        if("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpStatus.OK.value());
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                logger.debug("Entry!! :: doFilterInternal :: request => {}", request);
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getEmployeeCodeFromToken(jwtToken);
                logger.debug("doFilterInternal :: get user name => {}", username);
            } catch (IllegalArgumentException e) {
                logger.error("doFilterInternal :: An error occurred while fetching Username from Token = {}", e.getMessage());
            } catch (ExpiredJwtException ex) {
                logger.error("doFilterInternal :: The token has expired = {}", ex.getMessage());
                String isRefreshToken = request.getParameter("isRefreshToken");
                String requestURL = request.getRequestURL().toString();
                if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
                    // allow for Refresh Token creation if following conditions are true.
                    logger.debug("doFilterInternal :: Token is going to be Refresh");
                    allowForRefreshToken(ex, request);
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name());
                    BaseResponseEntity baseResponse = new BaseResponseEntity(response.getStatus(), HttpStatus.UNAUTHORIZED.name(), ErrorMessages.TOKEN_EXPIRE, null);
                    final ObjectMapper mapper = new ObjectMapper();
                    logger.debug("Exit!! :: doFilter :: exception => {}", ex.getMessage());
                    mapper.writeValue(response.getOutputStream(), baseResponse);
                }
            } catch (SignatureException e) {
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
//            response.setStatus(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name());
//            BaseResponseEntity baseResponse = new BaseResponseEntity(response.getStatus(), HttpStatus.UNAUTHORIZED.name(), "Please enter valid token with bearer key word", null);
//            final ObjectMapper mapper = new ObjectMapper();
////            logger.debug("Exit!! :: doFilter :: exception => {}", ex.getMessage());
//            mapper.writeValue(response.getOutputStream(), baseResponse);
        }
        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                logger.debug("doFilterInternal :: Token After validation");
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(jwtToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("doFilterInternal :: authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            logger.debug("Cannot set the Security Context");
        }

        try {
            filterChain.doFilter(request, response);
            logger.debug("Exit!! : doInternalFilter");
        } catch (
                Exception ex) {
            logger.error("doFilterInternal :: Filter Exception : {}", ex.getMessage());
        }
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        logger.debug("Entry!! :: allowForRefreshToken ");
        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());
        logger.debug("Exit!! :: allowForRefreshToken ");
    }
}


