package com.confeitaria.controller;

import com.confeitaria.handler.PedidoHandler;
import com.confeitaria.model.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PedidoController {

    private ObjectMapper mapper = new ObjectMapper();

    private PedidoHandler handler = new PedidoHandler();

    // POST /pedidos
    public String postPedido(String json) {
        try {

            Pedido p = mapper.readValue(json, Pedido.class);

            p = handler.salvar(p);

            return mapper.writeValueAsString(p);

        } catch (Exception e) {

            e.printStackTrace();

            return "{\"status\":\"ERROR\",\"mensagem\":\"" +
                    e.getMessage() + "\"}";
        }
    }

    // GET /pedidos/{id}
    public String getPedido(int id) {
        try {

            Pedido p = handler.buscarPorId(id);

            return mapper.writeValueAsString(p);

        } catch (Exception e) {

            return "{\"status\":\"ERROR\",\"mensagem\":\"Pedido nao encontrado\"}";
        }
    }

    // DELETE /pedidos/{id}
    public String deletePedido(int id) {
        try {

            handler.deletar(id);

            return "{\"status\":\"OK\",\"mensagem\":\"Pedido removido\"}";

        } catch (Exception e) {

            return "{\"status\":\"ERROR\",\"mensagem\":\"" +
                    e.getMessage() + "\"}";
        }
    }
}