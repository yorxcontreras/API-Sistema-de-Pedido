package com.confeitaria.controller;

import com.confeitaria.model.Pedido;
import com.confeitaria.service.PedidoService;
import com.confeitaria.service.PedidoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PedidoController {

    private ObjectMapper mapper = new ObjectMapper();
    private PedidoService service = new PedidoServiceImpl();

    // POST /pedidos
    public String postPedido(String json) {
        try {
            Pedido p = mapper.readValue(json, Pedido.class);
            p = service.salvar(p);

            return mapper.writeValueAsString(p);

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 MOSTRA O ERRO REAL
            return "{\"status\": \"ERROR\", \"mensagem\": \"" + e.getMessage() + "\"}";
        }
    }

    // GET /pedidos/{id}
    public String getPedido(int id) {
        try {
            Pedido p = service.buscarPorId(id);
            return mapper.writeValueAsString(p);

        } catch (Exception e) {
            return "{\"status\":\"ERROR\",\"mensagem\":\"Pedido nao encontrado\"}";
        }
    }

    // DELETE /pedidos/{id}
    public String deletePedido(int id) {
        try {
            service.deletar(id);

            return "{\"status\":\"OK\",\"mensagem\":\"Pedido removido\"}";

        } catch (Exception e) {
            return "{\"status\":\"ERROR\",\"mensagem\":\"" + e.getMessage() + "\"}";
        }
    }
}