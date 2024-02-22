package com.synoverge.leave.portal.controller;

import com.synoverge.leave.portal.DTO.BaseResponseEntity;
import com.synoverge.leave.portal.config.JwtTokenUtil;
import com.synoverge.leave.portal.models.dto.AuthDTO;
import com.synoverge.leave.portal.models.dto.EmployeeDTO;
import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.security.JwtUserDetailsService;
import com.synoverge.leave.portal.services.AuthService;
import com.synoverge.leave.portal.utility.Constants;
import com.synoverge.leave.portal.utility.SuccessMessage;
import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = Constants.baseUrl)
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<BaseResponseEntity> login(@RequestBody @Valid AuthDTO authDTO) {
            log.debug("Entry Auth Controller!! : Login with employee code {}", authDTO.getEmployeeCode());
            EmployeeDTO loggedInEmployee = authService.login(authDTO);
            BaseResponseEntity baseResponse = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), "User login successfully!!..", loggedInEmployee);
            log.debug("Exist Auth Controller!! : Login");
            return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.OK);
    }

    @PatchMapping("/auth/changePassword")
    public ResponseEntity<BaseResponseEntity> changePassword(@RequestBody AuthDTO authDTO) {
            log.debug("Entry Auth Controller!! : changePassword with employee code {}", authDTO.getEmployeeCode());
            String response = authService.changePassword(authDTO);
            BaseResponseEntity baseResponse = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), response , null);
            log.debug("Exist Auth Controller!! : changePassword");
            return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/auth/forgotPassword")
    public ResponseEntity<BaseResponseEntity> forgotPassword(@RequestBody AuthDTO authDTO) {
            log.debug("Entry Auth Controller!! : forgotPassword with employee code {}", authDTO.getEmployeeCode());
            String response = authService.forgotPassword(authDTO);
            BaseResponseEntity baseResponse = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), response , null);
            log.debug("Entry Auth Controller!! : forgotPassword :: Successfully");
            return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/auth/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        log.debug("Exist Auth Controller!! : refreshToken :: with request => {}",request);
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        Map<String, Object> expectedMap = jwtTokenUtil.getMapFromIoJsonWebTokenClaims(claims);
        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        BaseResponseEntity baseResponse = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), SuccessMessage.refreshTokenSuccess, token);
        log.debug("Exist Auth Controller!! : refreshToken :: Successfully");
        return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.OK);
    }
}
