package com.smallhowe.service;

import com.smallhowe.entity.Account;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
public interface SignInService {
    boolean isSignIn(Account account);
    void saveSignIn(Integer accountId, Integer giveExp);
}
