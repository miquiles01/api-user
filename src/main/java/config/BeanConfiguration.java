package config;

import domain.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import port.output.UserOutputPort;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserUseCase userUseCase(UserOutputPort outputPort) {
        return new UserUseCase(outputPort);
    }
}