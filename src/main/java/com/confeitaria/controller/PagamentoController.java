package com.confeitaria.controller;

import com.confeitaria.handler.PagamentoHandler;

public class PagamentoController {

    private PagamentoHandler handler = new PagamentoHandler();

    // POST /pagamentos/{pedidoId}
    public String confirmarPagamento(int pedidoId) {

        try {

            System.out.println(
                    "[API Principal] Enviando pedido ID "
                            + pedidoId
                            + " para validação na API Externa..."
            );

            boolean aprovadoNaApiExterna =
                    handler.processarPagamento(pedidoId);

            if (!aprovadoNaApiExterna) {

                return "{\"status\":\"ERROR\",\"mensagem\":\"Pagamento recusado pela API externa ou Gateway fora do ar.\"}";
            }

            handler.atualizarStatus(pedidoId);

            return "{\"status\":\"OK\",\"mensagem\":\"Pagamento integrado e aprovado com sucesso!\"}";

        } catch (Exception e) {

            return "{\"status\":\"ERROR\",\"mensagem\":\"Falha no pagamento: "
                    + e.getMessage() + "\"}";
        }
    }
}