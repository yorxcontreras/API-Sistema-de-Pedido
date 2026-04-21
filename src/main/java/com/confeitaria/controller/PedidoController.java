package com.confeitaria.controller;

import com.confeitaria.model.Pedido;
import com.confeitaria.service.PedidoService;
import com.confeitaria.service.PedidoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PedidoController {
    private ObjectMapper mapper = new ObjectMapper();
    private PedidoService service = new PedidoServiceImpl();

    // POST /pedidos (Criar pedido)
    public String postPedido(String json) {
        try {
            Pedido p = mapper.readValue(json, Pedido.class);
            p = service.salvar(p);
            return mapper.writeValueAsString(p);
        } catch (Exception e) {
            return "{\"status\": 400, \"erro\": \"Dados do pedido invalidos\"}";
        }
    }

    // GET /pedidos/{id} (Buscar pedido)
    public String getPedido(int id) {
        try {

            Pedido p = service.buscarPorId(id);
            // AJUSTE: Retorne o pedido real em JSON, não apenas uma mensagem!
            return mapper.writeValueAsString(p);
        } catch (Exception e) {
            return "{\"erro\": \"Pedido nao encontrado\"}";
        }
    }

    // DELETE /pedidos/{id}
    public String deletePedido(int id) {
        try {
            // AJUSTE:chamando a regra de negocio
            service.deletar(id);
            return "{\"status\": \"Pedido " + id + " removido com sucesso\"}";
        } catch (Exception e) {
            // Se a Service barrar (pedido PAGO), a mensagem aparece aqui
            return "{\"status\": 400, \"erro\": \"" + e.getMessage() + "\"}";
        }
    }
}