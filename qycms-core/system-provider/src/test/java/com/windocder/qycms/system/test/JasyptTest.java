package com.windocder.qycms.system.test;

import com.windcoder.qycms.QycmsApplication;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QycmsApplication.class)
public class JasyptTest {
    @Autowired
    StringEncryptor stringEncryptor;
//    @Test
//    public void contextLoads() {
//        System.out.println("测试中1");
//        // 断言
//        TestCase.assertEquals(1,1);
//    }


    @Test
    public void encryptPwd() {
        String result   = stringEncryptor.encrypt("127.0.0.1");
        String result2 = stringEncryptor.encrypt("927tZR%rVms%");
        System.out.println("结果:" + result +" , d: " + result2);
    }
}
