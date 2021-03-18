package com.windcoder.qycms.common.test;

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
        String result   = stringEncryptor.encrypt("qycmsblog");
        String result2 = stringEncryptor.encrypt("nWAjHN8es4jn3TsH");
        System.out.println("结果:" + result +" , d: " + result2);

        String result3   = stringEncryptor.encrypt("114.116.87.84");
        String result4 = stringEncryptor.encrypt("Mkg7yFKvIXIHBh0I");
        System.out.println("结果:" + result3 +" , d: " + result4);
    }
}
