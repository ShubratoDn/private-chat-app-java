package com.chatapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//adding end points
//		registry.addEndpoint("/sdn-web-sock").setAllowedOrigins("*").withSockJS(); //ERROR ASHE
		registry.addEndpoint("/sdn-web-sock").setAllowedOriginPatterns("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//
		registry.enableSimpleBroker("/public-chatroom","/user");
		registry.setApplicationDestinationPrefixes("/app");		
		registry.setUserDestinationPrefix("/user");				
	}	
}
