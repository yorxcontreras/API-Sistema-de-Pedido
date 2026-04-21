package com.confeitaria.service;
import com.confeitaria.model.Pedido;

// contrato de metodos
public interface PedidoService {
   // salva um novo pedido
    Pedido salvar (Pedido pedido);

    // buscar um pedido com id
    Pedido buscarPorId(int id);

    // deletar pedido
    void deletar(int id);

    // transição de status de pagamentos
    void atualizarStatus (int id);


}