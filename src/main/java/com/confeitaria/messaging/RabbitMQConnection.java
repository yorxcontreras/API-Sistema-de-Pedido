package com.confeitaria.messaging;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnection {

    private static Connection connection = null;

    public static Connection getConnection() throws IOException, TimeoutException {
        // Se a conexão não existir ou tiver sido fechada, cria uma nova
        if (connection == null || !connection.isOpen()) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            factory.setUsername("guest");
             factory.setPassword("guest");

            connection = factory.newConnection();
        }
        return connection;
    }
}