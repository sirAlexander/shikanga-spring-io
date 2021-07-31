package com.learnk8s.horizontalpodautoscaler;

import com.learnk8s.horizontalpodautoscaler.queue.QueueService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

@SpringBootApplication
@EnableJms
public class HorizontalPodAutoscalerApplication implements JmsListenerConfigurer {

    private final QueueService queueService;

    public HorizontalPodAutoscalerApplication(QueueService queueService) {
        this.queueService = queueService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HorizontalPodAutoscalerApplication.class, args);
    }

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar jmsListenerEndpointRegistrar) {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId("myId");
        endpoint.setDestination("queueName");
        endpoint.setMessageListener(queueService);
        jmsListenerEndpointRegistrar.registerEndpoint(endpoint);

    }
}
