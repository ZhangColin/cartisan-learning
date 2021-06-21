package com.example.redislistener;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class RedisConfig {

    @Bean
    public RedisSerializer<?> genericJackson2JsonRedisSerializer(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        final ObjectMapper objectMapper = jacksonObjectMapperBuilder.build();

        //启用反序列化所需的类型信息,在属性中添加@class
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // 配置 null 值序列化器
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<?> redisSerializer) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);

        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public ValueOperations<?, ?> valueOperations(RedisTemplate<?,?> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public HashOperations<?, ?, ?> hashOperations(RedisTemplate<?,?> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ListOperations<?, ?> listOperations(RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<?, ?> setOperations(RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<?, ?> zSetOperations(RedisTemplate<?, ?> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        return container;
    }

}
