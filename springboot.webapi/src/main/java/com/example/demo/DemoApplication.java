package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@EnableRetry
@EnableJpaRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {

        var restTemplate = new RestTemplate();
        //消息转换器列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        //配置消息转换器StringHttpMessageConverter，并设置utf‐8支持中文字符集
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        return new RestTemplate();
    }

    /**
     * 负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate LoadBalancedRestTemplate(){
        return restTemplate();
    }

    /**
     * 重试
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate retryRestTemplate(RestTemplateBuilder builder) {

        return builder.setConnectTimeout(Duration.ofSeconds(10))
                    .setReadTimeout(Duration.ofSeconds(120))
                    .additionalInterceptors((request, body, execution) -> {

            var retryTemplate = RetryTemplate.builder().maxAttempts(10)
                    .exponentialBackoff(100, 2, 10000)
                    .retryOn(RestClientException.class).build();
            return retryTemplate.execute(context -> execution.execute(request, body));
        }).build();
    }

}
