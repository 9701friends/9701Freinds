package friends.aidelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class AiDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiDeliveryApplication.class, args);
    }

}
