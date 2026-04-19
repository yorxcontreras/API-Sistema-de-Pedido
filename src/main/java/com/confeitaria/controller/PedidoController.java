package com.confeitaria.controller;

import com.confeitaria.model.Pedido;
import com.confeitaria.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PedidoController {
    private ObjectMapper mapper = new ObjectMapper();
    private PedidoService service; // Interface que a Pessoa 2 vai implementar

    // POST /pedidos (Criar pedido)
    public String postPedido(String json) {
        try {
            Pedido p = mapper.readValue(json, Pedido.class);
            // service.salvar(p); 
            return mapper.writeValueAsString(p);
        } catch (Exception e) {
            return "{\"status\": 400, \"erro\": \"Dados do pedido invalidos\"}";
        }
    }

    // GET /pedidos/{id} (Buscar pedido)
    public String getPedido(int id) {
        try {
            // Pedido p = service.buscarPorId(id);
            return "{\"mensagem\": \"Retornando dados do pedido " + id + "\"}";
        } catch (Exception e) {
            return "{\"erro\": \"Pedido nao encontrado\"}";
        }
    }

    // DELETE /pedidos/{id} (Cancelar)
    public String deletePedido(int id) {
        return "{\"status\": \"Pedido " + id + " removido\"}";
    }
}