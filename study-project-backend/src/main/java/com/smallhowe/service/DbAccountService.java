package com.smallhowe.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DbAccountService extends UserDetailsService {
    int sendValidateEmail(String email, String session);
    int validateAndRegister(String username, String password, String email, String code,String sessionId);
}
