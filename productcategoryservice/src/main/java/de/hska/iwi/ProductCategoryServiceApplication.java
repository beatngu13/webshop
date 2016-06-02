package de.hska.iwi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
@EnableCaching
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ProductCategoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCategoryServiceApplication.class, args);
    }

}
