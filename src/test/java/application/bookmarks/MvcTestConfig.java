package application.bookmarks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@Configuration
public class MvcTestConfig {

    @Bean
    public MvcTestHelper testHelper() {
        return new MvcTestHelper();
    }

    @Bean
    public HttpMessageConverter messageConverter(HttpMessageConverter<?>[] converters) {
        HttpMessageConverter mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);

        return mappingJackson2HttpMessageConverter;
    }

}
