package com.confeitaria.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IntegracaoPagamentoService {

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    // IMPORTANTE:
    // usando 127.0.0.1 evita problemas de localhost/IPv6
    private final String URL_GATEWAY =
            "http://127.0.0.1:8081/gateway/pagar";

    public boolean enviarParaGatewayExterno(
            int pedidoId,
            double valor
    ) {

        try {

            // ================= JSON =================
            String jsonPayload = String.format(
                    java.util.Locale.US,
                    """
                    {
                        "pedidoId": %d,
                        "valorTotal": %.2f
                    }
                    """,
                    pedidoId,
                    valor
            );

            System.out.println("\n==============================");
            System.out.println("[API Principal] INICIANDO PAGAMENTO");
            System.out.println("==============================");

            System.out.println(
                    "[API Principal] Pedido ID: "
                            + pedidoId
            );

            System.out.println(
                    "[API Principal] Valor Total: "
                            + valor
            );

            System.out.println(
                    "[API Principal] JSON enviado:"
            );

            System.out.println(jsonPayload);

            // ================= REQUEST =================
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(URL_GATEWAY))
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .POST(
                                    HttpRequest
                                            .BodyPublishers
                                            .ofString(jsonPayload)
                            )
                            .build();

            System.out.println(
                    "[API Principal] Enviando requisição para Gateway..."
            );

            // ================= RESPONSE =================
            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "[API Principal] STATUS HTTP: "
                            + response.statusCode()
            );

            System.out.println(
                    "[API Principal] RESPOSTA DO GATEWAY:"
            );

            System.out.println(response.body());

            // ================= SUCESSO =================
            if (response.statusCode() == 200) {

                System.out.println(
                        "[API Principal] Pagamento APROVADO!"
                );

                return true;
            }

            // ================= FALHA =================
            System.out.println(
                    "[API Principal] Gateway retornou erro!"
            );

            return false;

        } catch (Exception e) {

            System.out.println("\n==============================");
            System.out.println("[API Principal] ERRO NO GATEWAY");
            System.out.println("==============================");

            e.printStackTrace();

            return false;
        }
    }
}