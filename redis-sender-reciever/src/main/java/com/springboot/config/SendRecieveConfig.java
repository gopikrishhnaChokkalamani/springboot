package com.springboot.config;

import java.util.UUID;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.springboot.reciever.RedisReciever;

@Configuration
public class SendRecieveConfig {

	@Bean
	JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		// If you have to connect to redis server deployed somewhere else, use the below
		// If you have redis in your local machine, no need to configure this, redis
		// will auto map the connection
		// factory.setHostName(hostName);
		// factory.setPort(port);
		return factory;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	@Bean
	ChannelTopic topic() {
		return new ChannelTopic(UUID.randomUUID().toString());
		// You can create multiple named topics,
		// return new ChannelTopic("myTopic-redis-communication");
		// but its recommended that you can create
		// topics with random generated Ids
	}

	// This is the Receiver Configuration
	@Bean
	RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.addMessageListener(new MessageListenerAdapter(new RedisReciever()), topic());
		container.setTaskExecutor(Executors.newFixedThreadPool(4));
		return container;
	}
}
