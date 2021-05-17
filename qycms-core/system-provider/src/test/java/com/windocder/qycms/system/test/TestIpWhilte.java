package com.windocder.qycms.system.test;

import com.windcoder.qycms.QycmsApplication;
import com.windcoder.qycms.system.utils.IpWhilteUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QycmsApplication.class)
public class TestIpWhilte {

    @Test
    public  void testMain() {
        System.out.println("192.168.0".matches("192.*"));
        System.out.println(IpWhilteUtil.isPermited("192.168.0.1","192.*"));

        String ipWhilte = "1.168.1.1;" + //设置单个IP的白名单 //
                // "192.*;" //设置ip通配符,对一个ip段进行匹配
                "192.168.3.17-192.168.3.38;" //设置一个IP范围
                +"192.168.4.0/24;"
                + "127.0.0.1"; //設置一个网段
        System.out.println(IpWhilteUtil.isPermited("127.0.0.2",ipWhilte));
    }
}
