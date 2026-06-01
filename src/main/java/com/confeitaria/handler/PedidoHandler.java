package com.confeitaria.handler;

import com.confeitaria.model.Pedido;
import com.confeitaria.service.PedidoService;
import com.confeitaria.service.PedidoServiceImpl;

public class PedidoHandler {

    private PedidoService service = new PedidoServiceImpl();

    public Pedido salvar(Pedido pedido) {
        return service.salvar(pedido);
    }

    public Pedido buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    public void deletar(int id) {
        service.deletar(id);
    }

    public void atualizarStatus(int id) {
        service.atualizarStatus(id);
    }
}