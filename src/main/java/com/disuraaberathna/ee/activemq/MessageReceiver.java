package com.disuraaberathna.ee.activemq;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageReceiver {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("ActiveMQ-Client-App-01");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("News");

            MessageConsumer consumer = session.createConsumer(topic);

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("Received message: " + message.getBody(String.class));
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
