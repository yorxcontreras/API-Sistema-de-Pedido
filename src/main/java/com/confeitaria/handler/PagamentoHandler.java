package com.confeitaria.handler;

import com.confeitaria.model.Pedido;
import com.confeitaria.service.IntegracaoPagamentoService;
import com.confeitaria.service.PedidoService;
import com.confeitaria.service.PedidoServiceImpl;

public class PagamentoHandler {

    private PedidoService service = new PedidoServiceImpl();

    private IntegracaoPagamentoService integracaoService =
            new IntegracaoPagamentoService();

    public boolean processarPagamento(int pedidoId) throws Exception {

        Pedido pedido = service.buscarPorId(pedidoId);

        return integracaoService.enviarParaGatewayExterno(
                pedidoId,
                pedido.valorTotal
        );
    }

    public void atualizarStatus(int pedidoId) {
        service.atualizarStatus(pedidoId);
    }
}