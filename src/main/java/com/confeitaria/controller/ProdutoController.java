package com.confeitaria.controller;

import com.confeitaria.model.Produto;
import com.confeitaria.service.ProdutoService;
import com.confeitaria.service.ProdutoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProdutoController {

    private ObjectMapper mapper = new ObjectMapper();
    private ProdutoService service = new ProdutoServiceImpl();

    // POST /produtos
    public String cadastrarProduto(String json) {
        try {
            Produto prod = mapper.readValue(json, Produto.class);
            service.salvar(prod);

            return mapper.writeValueAsString(prod);

        } catch (Exception e) {
            return "{\"status\":\"ERROR\",\"mensagem\":\"Erro ao criar produto\"}";
        }
    }

    // GET /produtos
    public String listarProdutos() {
        try {
            return mapper.writeValueAsString(service.listarTodos());
        } catch (Exception e) {
            return "[]";
        }
    }

    // GET /produtos/{id}
    public String buscarProduto(int id) {
        try {
            Produto p = service.buscarPorId(id);
            return mapper.writeValueAsString(p);

        } catch (Exception e) {
            return "{\"status\":\"ERROR\",\"mensagem\":\"Produto nao encontrado\"}";
        }
    }

    // UPDATE/produtos/{id}
    public String atualizarProduto(String json) {
        try {
            Produto p = mapper.readValue(json, Produto.class);
            service.atualizar(p);
            return "{\"status\": \"Produto atualizado com sucesso\"}";
        } catch (Exception e) {
            return "{\"erro\": \"Erro ao atualizar produto\"}";
        }
    }
    // DELETE /produtos/{id}
    public String deletarProduto(int id) {
        try {
            service.deletar(id);
            return "{\"status\":\"OK\",\"mensagem\":\"Produto removido\"}";

        } catch (Exception e) {
            return "{\"status\":\"ERROR\",\"mensagem\":\"Erro ao deletar\"}";
        }
    }
}