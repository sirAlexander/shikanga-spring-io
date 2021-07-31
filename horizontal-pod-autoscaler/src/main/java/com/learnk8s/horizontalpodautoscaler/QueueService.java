package com.learnk8s.horizontalpodautoscaler;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.util.Collections;

@Component
public class QueueService {

    private final JmsTemplate jmsTemplate;

    public QueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public boolean isUp() {
        var connection = jmsTemplate.getConnectionFactory();
        try {
            connection.createConnection().close();
            return true;
        } catch (JMSException e){
            e.printStackTrace();
        }
        return false;
    }

    public int pendingJobs(String queueName) {
        return jmsTemplate.browse(
                queueName,
                (s, queueBrowser) -> Collections.list(queueBrowser.getEnumeration()).size()
        );
    }
}
