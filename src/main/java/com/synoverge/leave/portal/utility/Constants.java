package com.synoverge.leave.portal.utility;

public class Constants {
    public final static String baseUrl = "/api/v1/";

    public final static String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/api-docs",
            "/api-docs/swagger-config",
            "/webjars/**"
    };

}
