package com.confeitaria.controller;

import com.confeitaria.service.PedidoService;
import com.confeitaria.service.PedidoServiceImpl;

public class PagamentoController {

    private PedidoService service = new PedidoServiceImpl();
    
    // POST /pagamentos/{pedidoId}
    // Este metodo simula a chamada de um sistema externo confirmando o pagamento
    public String confirmarPagamento(int pedidoId) {
        try {
            // Logica: Chama o service para mudar o status do pedido para "PAGO"
            service.atualizarStatus(pedidoId);
            
            return "{\"pedidoId\": " + pedidoId + ", \"status\": \"PAGO\", \"mensagem\": \"Pagamento aprovado com sucesso\"}";
        } catch (Exception e) {
            return "{\"erro\": \"Nao foi possivel processar pagamento do pedido " + pedidoId + "\"}";
        }
    }
}