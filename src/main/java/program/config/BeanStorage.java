package program.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanStorage {
    @Bean
    public PasswordEncoder encoder() {      // чтобы работал в UserService @Autowired PasswordEncoder passwordEncoder;
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {      // для конвертера entity - dto
        var mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper;
    }
}
