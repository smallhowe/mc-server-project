package com.smallhowe.service;

import com.smallhowe.entity.Account;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UserService {
    int uploadAvatar(MultipartFile avatar, Account account);
}
