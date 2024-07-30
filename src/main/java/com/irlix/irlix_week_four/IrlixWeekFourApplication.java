package com.irlix.irlix_week_four;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class IrlixWeekFourApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrlixWeekFourApplication.class, args);
    }

}
