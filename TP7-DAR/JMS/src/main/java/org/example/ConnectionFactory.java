package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class ConnectionFactory {
    public static void main(String[] args) {
        // Spécifiez l'URL du broker
        String brokerURL = "tcp://localhost:61616";
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        System.out.println("ConnectionFactory configured with broker URL: " + connectionFactory.getBrokerURL());

        Connection connection = null;
        Session session = null;
        try {
            // Utilisez createConnection pour établir la connexion vers le broker.
            connection = connectionFactory.createConnection();
            System.out.println("Connection created successfully.");

            // Démarrez la connexion à l'aide de la méthode start.
            connection.start();
            System.out.println("Connection started successfully.");

            // Créez une session transactionnelle avec AUTO_ACKNOWLEDGE
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            System.out.println("Session created successfully.");

            // Créez un objet de type Topic
            Topic topic = session.createTopic("MyTopic");
            System.out.println("Topic 'MyTopic' created successfully.");

            // Créez un MessageProducer
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            System.out.println("MessageProducer configured for non-persistent messages.");

            // Créez le message
            TextMessage message = session.createTextMessage("Hello World! From: Producer");
            System.out.println("Sending message: '" + message.getText() + "'");
            producer.send(message);

            // Validez la transaction
            session.commit();
            System.out.println("Session committed.");
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                    System.out.println("Session closed.");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
