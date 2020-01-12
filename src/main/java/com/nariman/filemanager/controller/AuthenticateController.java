package com.nariman.filemanager.controller;

import com.nariman.filemanager.model.AuthenticationRenewRequest;
import com.nariman.filemanager.model.AuthenticationRequest;
import com.nariman.filemanager.model.AuthenticationResponse;
import com.nariman.filemanager.service.JwtService;
import com.nariman.filemanager.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/authenticate/validate")
    public ResponseEntity<String> check() {
        log.info("validation request received");
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/authenticate/renew")
    public ResponseEntity<AuthenticationResponse> renewAuthenticationToken(@RequestBody AuthenticationRenewRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        throw new RuntimeException("not implemented");

    }

}