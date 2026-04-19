package com.confeitaria.controller;

import com.confeitaria.model.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProdutoController {
    private ObjectMapper mapper = new ObjectMapper();

    // POST /produtos (Cadastrar doce/bolo)
    public String cadastrarProduto(String json) {
        try {
            Produto prod = mapper.readValue(json, Produto.class);
            return "{\"status\": \"Produto " + prod.getNome() + " cadastrado!\"}";
        } catch (Exception e) {
            return "{\"erro\": \"Erro ao processar produto\"}";
        }
    }

    // GET /produtos (Listar todos)
    public String listarProdutos() {
        return "[{\"id\": 1, \"nome\": \"Bolo de Pote\", \"preco\": 15.0}]";
    }
}