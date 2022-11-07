package com.geo.mapper.continents;

import com.geo.mapper.continents.models.Continent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@SpringBootApplication
public class ContinentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContinentsApplication.class, args);
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/**");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RedisTemplate<Long, Continent> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Long, Continent> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		return template;
	}


}
