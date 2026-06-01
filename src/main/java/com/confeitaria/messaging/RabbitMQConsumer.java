package com.confeitaria.messaging;

import com.rabbitmq.client.*;

public class RabbitMQConsumer {

    private static final String QUEUE_NAME = "fila.pagamentos";

    public static void main(String[] args) {
        try {
            // Conecta ao RabbitMQ usando o gestor de conexões
            Connection connection = RabbitMQConnection.getConnection();
            Channel channel = connection.createChannel();

            // Garante que a fila está criada
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);


            System.out.println("[RABBITMQ] Aguardando mensagens na fila '" + QUEUE_NAME + "'...");


            // Define o comportamento de quando uma nova mensagem chegar
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String mensagem = new String(delivery.getBody(), "UTF-8");

                System.out.println("\n[RABBITMQ] Nova mensagem de pagamento recebida!");
                System.out.println("Conteúdo: " + mensagem);

            };

            // Liga o consumo na fila com auto-ack ativo (true)
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

        } catch (Exception e) {
            System.err.println("[RABBITMQ - ERRO] Erro crítico no Consumer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}