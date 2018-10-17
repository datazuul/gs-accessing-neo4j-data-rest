package hello.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.Text;
import hello.Translation;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {
  "hello"
})
public class SpringConfig extends WebMvcConfigurerAdapter {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // support for @ResponseBody of type String
    final StringHttpMessageConverter stringHMC = new StringHttpMessageConverter(StandardCharsets.UTF_8);
    // supported MediaTypes for stringHMC are by default set to: "text/plain" and MediaType.ALL
    converters.add(stringHMC);

    // support for @ResponseBody of type Object: convert object to JSON
    // used in ApiController
    converters.add(mappingJackson2HttpMessageConverter());
  }

  @Bean
  public HttpMessageConverter<?> mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper());
    return converter;
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

    objectMapper.addMixIn(Text.class, TextMixIn.class);
    objectMapper.addMixIn(Translation.class, TranslationMixIn.class);

    return objectMapper;
  }
}
