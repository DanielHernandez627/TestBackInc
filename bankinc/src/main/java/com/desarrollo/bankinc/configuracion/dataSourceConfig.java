package com.desarrollo.bankinc.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class dataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        String dbUrl = env.getProperty("spring.datasource.url");
        String dbUsername = env.getProperty("spring.datasource.username");
        String dbPassword = env.getProperty("spring.datasource.password");
        String dbDriver = env.getProperty("spring.datasource.driver-class-name");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);


        return dataSource;
    }
}
