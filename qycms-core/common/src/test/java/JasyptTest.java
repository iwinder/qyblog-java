import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JasyptTest {

    //注入StringEncryptor
    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void encry() {
        //加密root
        String username = encryptor.encrypt("####");
        System.out.println(username);
        //加密123
        String password = encryptor.encrypt("####");
        System.out.println(password);

        //加密redishost
        String redishost = encryptor.encrypt("#####");
        System.out.println(redishost);

        String redispassword = encryptor.encrypt("#####");
        System.out.println(redispassword);

        String redisPort = encryptor.encrypt("#####");
        System.out.println(redispassword);
    }

}
