package friends.aidelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AiDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiDeliveryApplication.class, args);
    }

}
