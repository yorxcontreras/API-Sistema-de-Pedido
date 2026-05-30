package com.confeitaria.controller;

import com.confeitaria.model.Pedido;
import com.confeitaria.service.IntegracaoPagamentoService;
import com.confeitaria.service.PedidoService;
import com.confeitaria.service.PedidoServiceImpl;

public class PagamentoController {

    private PedidoService service = new PedidoServiceImpl();
    // Instância do serviço que criamos para falar com a API externa
    private IntegracaoPagamentoService integracaoService = new IntegracaoPagamentoService();

    // POST /pagamentos/{pedidoId}
    public String confirmarPagamento(int pedidoId) {
        try {
            // 1. Buscamos o pedido para saber o valor real que deve ser enviado ao Gateway
            Pedido pedido = service.buscarPorId(pedidoId);
            
            System.out.println("[API Principal] Enviando pedido ID " + pedidoId + " para validação na API Externa...");

            // 2. Fazemos a chamada HTTP para a API Externa do seu colega (Parte 2)
            boolean aprovadoNaApiExterna = integracaoService.enviarParaGatewayExterno(pedidoId, pedido.valorTotal);

            // 3. Se a API externa recusar ou estiver fora do ar, barramos aqui
            if (!aprovadoNaApiExterna) {
                return "{\"status\":\"ERROR\",\"mensagem\":\"Pagamento recusado pela API externa ou Gateway fora do ar.\"}";
            }

            // 4. Se a API externa aprovou, atualizamos o status no nosso sistema
            service.atualizarStatus(pedidoId);

            return "{\"status\":\"OK\",\"mensagem\":\"Pagamento integrado e aprovado com sucesso!\"}";

        } catch (Exception e) {
            return "{\"status\":\"ERROR\",\"mensagem\":\"Falha no pagamento: " + e.getMessage() + "\"}";
        }
    }
}