package com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.TestPropertySource;

import javax.script.ScriptException;
import javax.sql.DataSource;
import java.sql.SQLException;

//@Configuration
//@ComponentScan
//@TestPropertySource(locations = { "classpath:application.properties" })
public class ConfigTest {

//    @Bean
//    public DataSource dataSource() throws ScriptException, SQLException {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriver(new Driver());
//        dataSource.setUrl("jdbc:mariadb://localhost:3306/sample");
//        dataSource.setUsername("user");
//        dataSource.setPassword("user");
//        return dataSource;
//    }
}
