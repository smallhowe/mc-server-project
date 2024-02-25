package com.smallhowe.service;

import com.smallhowe.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    int sendValidateEmail(String email, String session, boolean hasAccount);
    int validateAndRegister(String username, String password, String email, String code,String sessionId);
    String validateOnly(String email, String code,String sessionId);

    boolean resetPassword(String password, String email);

}
