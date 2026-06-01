package com.confeitaria.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RabbitMQProducer {

    private static final String QUEUE_NAME = "fila.pagamentos";

    public static void enviarMensagem(String mensagem) {
        try {
            // Recupera a conexão centralizada (não fecha a conexão no fim para poder reutilizar)
            Connection connection = RabbitMQConnection.getConnection();

            // Abre um Channel apenas para este envio e fecha-o automaticamente no fim do try
            try (Channel channel = connection.createChannel()) {

                // Garante que a fila existe no RabbitMQ
                channel.queueDeclare(
                        QUEUE_NAME,
                        true,
                        false,
                        false,
                        null
                );

                // Publica a mensagem na fila usando UTF-8 para evitar problemas com acentos
                channel.basicPublish(
                        "",
                        QUEUE_NAME,
                        null,
                        mensagem.getBytes("UTF-8")
                );

                System.out.println("[RABBITMQ] Mensagem enviada com sucesso para a fila '" + QUEUE_NAME + "':");
                System.out.println(mensagem);
            }

        } catch (Exception e) {
            System.err.println("[RABBITMQ - ERRO] Falha ao enviar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}