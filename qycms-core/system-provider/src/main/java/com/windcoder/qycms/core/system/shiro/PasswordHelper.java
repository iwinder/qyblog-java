package com.windcoder.qycms.core.system.shiro;

import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.utils.SecurityUtils;
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

    public static final String ALGORITHM = "SHA-1";

    public static final int HASHITERATIONS = 1024;

    private static final int SALT_SIZE = 22;


    public static String generateSalt(){
        byte[] salt = SecurityUtils.generateSalt(SALT_SIZE);
        return SecurityUtils.encodeHex(salt);
    }

    public static String encryptPassword(User user) {
//        String newPassword = new SimpleHash(
//                ALGORITHM,
//                user.getPassword(),
//                ByteSource.Util.bytes(user.getCredentialsSalt()),
//                HASHITERATIONS).toHex();
        return  user.getPassword();
    }




}
