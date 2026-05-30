package com.confeitaria.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IntegracaoPagamentoService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    // URL da API externa que seu colega vai criar (rodando em outra porta, ex: 8081)
    private final String URL_GATEWAY = "http://localhost:8081/gateway/pagar";

    public boolean enviarParaGatewayExterno(int pedidoId, double valor) {
        try {
            // Criando o JSON que vamos enviar para a API externa
            String jsonPayload = String.format("{\"pedidoId\": %d, \"valorTotal\": %.2f}", pedidoId, valor);

            // Montando a requisição HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GATEWAY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Enviando a requisição e aguardando a resposta
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Se o gateway responder status 200 (OK), o pagamento foi aceito na API externa
            if (response.statusCode() == 200) {
                System.out.println("[API Principal] Resposta do Gateway: " + response.body());
                return true; 
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("[API Principal] Erro ao conectar com o Gateway Externo: " + e.getMessage());
            return false;
        }
    }
}