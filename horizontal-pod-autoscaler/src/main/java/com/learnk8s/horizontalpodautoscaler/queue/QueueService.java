package com.learnk8s.horizontalpodautoscaler.queue;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class QueueService implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);

    private final JmsTemplate jmsTemplate;

    private AtomicInteger counter;

    public QueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public boolean isUp() {
        var connection = jmsTemplate.getConnectionFactory();
        try {
            assert connection != null;
            connection.createConnection().close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void send(String destination, String message) {
        LOGGER.info("sending message='{}' to destination='{}'", message, destination);
        jmsTemplate.convertAndSend(destination, message);
    }

    public int pendingJobs(String queueName) {
        return jmsTemplate.browse(
                queueName,
                (s, queueBrowser) -> Collections.list(queueBrowser.getEnumeration()).size()
        );
    }

    public int completedJobs() {
        return counter.get();
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ActiveMQTextMessage) {
            ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
            try {
                LOGGER.info("Processing task " + textMessage.getText());
                Thread.sleep(5000);
                LOGGER.info("Completed task " + textMessage.getText());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            counter.incrementAndGet();
        } else {
            LOGGER.error("Message is not a text message " + message.toString());
        }

    }


}
