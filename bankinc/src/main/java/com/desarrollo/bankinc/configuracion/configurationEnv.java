package com.desarrollo.bankinc.configuracion;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configurationEnv {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
}
