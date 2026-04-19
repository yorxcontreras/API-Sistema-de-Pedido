package com.confeitaria.controller;

public class PagamentoController {
    
    // POST /pagamentos/{pedidoId}
    // Este metodo simula a chamada de um sistema externo confirmando o pagamento
    public String confirmarPagamento(int pedidoId) {
        try {
            // Logica: Chama o service para mudar o status do pedido para "PAGO"
            // service.atualizarStatus(pedidoId, "PAGO");
            
            return "{\"pedidoId\": " + pedidoId + ", \"status\": \"PAGO\", \"mensagem\": \"Pagamento aprovado com sucesso\"}";
        } catch (Exception e) {
            return "{\"erro\": \"Nao foi possivel processar pagamento do pedido " + pedidoId + "\"}";
        }
    }
}