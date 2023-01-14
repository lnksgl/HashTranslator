package hastranslator.config;

import hastranslator.utils.DecodeHashCustomApi;
import hastranslator.utils.DecodeHashCustomApiImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecodeRequestConfig {

    @Bean
    public DecodeHashCustomApi decodeHashApi() {
        return new DecodeHashCustomApiImpl();
    }
}
