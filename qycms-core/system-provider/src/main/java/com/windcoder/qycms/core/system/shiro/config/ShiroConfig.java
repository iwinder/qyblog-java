package com.windcoder.qycms.core.system.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Bean;

public class ShiroConfig {
    private static final String ALGORITHM = "SHA-256";

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(ALGORITHM);//散列算法:这里使用SHA-256算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 SHA-256(SHA-256(""));
        return hashedCredentialsMatcher;
    }

}
