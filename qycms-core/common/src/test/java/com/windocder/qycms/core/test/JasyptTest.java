package com.windocder.qycms.core.test;

import com.windcoder.qycms.QycmsApplication;
import junit.framework.TestCase;
import org.jasypt.encryption.StringEncryptor;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QycmsApplication.class)

//@SpringBootTest()
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
        String result = null;
//         result = stringEncryptor.encrypt("root");
        System.out.println("结果:" + result);
    }
}
