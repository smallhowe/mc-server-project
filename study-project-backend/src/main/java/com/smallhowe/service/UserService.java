package com.smallhowe.service;

import com.smallhowe.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int uploadAvatar(MultipartFile avatar, Account account);
    int signIn(Account account);
    int bindGameId(Account account, String gameId);

    int updatePassword(Account account);

}
