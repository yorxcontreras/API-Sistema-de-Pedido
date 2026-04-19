package com.confeitaria.service;
import com.confeitaria.model.Pedido;

public interface PedidoService {
    void salvar(Pedido p) throws Exception;
    void atualizarStatus(int id, String status) throws Exception;
}