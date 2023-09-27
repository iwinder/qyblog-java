package com.windcoder.qycms.core.test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JasyptTest {

	@Autowired
	StringEncryptor stringEncryptor;

	@Test
	public void encryptPwd() {
		String result = stringEncryptor.encrypt("root");
//		 result = stringEncryptor.encrypt("114.116.87.84");
		 result = stringEncryptor.encrypt("8YsCi3SYdF8e5idh");
		System.out.println(result);
	}
}
