package com.confeitaria.controller;

import com.confeitaria.service.PedidoService;
import com.confeitaria.service.PedidoServiceImpl;

public class PagamentoController {

    private PedidoService service = new PedidoServiceImpl();

    // POST /pagamentos/{pedidoId}
    public String confirmarPagamento(int pedidoId) {
        try {
            service.atualizarStatus(pedidoId);

            return "{\"status\":\"OK\",\"mensagem\":\"Pagamento aprovado\"}";

        } catch (Exception e) {
            return "{\"status\":\"ERROR\",\"mensagem\":\"Falha no pagamento\"}";
        }
    }
}