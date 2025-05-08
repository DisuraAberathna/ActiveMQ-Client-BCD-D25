package com.disuraaberathna.ee.activemq;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("ActiveMQ-Client-App-01");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("News");

            MessageProducer producer = session.createProducer(topic);

            Message message = session.createTextMessage("Hello World, This is a message sender by ActiveMQ Client");
            producer.send(message);

            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
