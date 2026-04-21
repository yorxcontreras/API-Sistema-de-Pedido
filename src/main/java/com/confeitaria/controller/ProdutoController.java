package com.confeitaria.controller;

import com.confeitaria.model.Produto;
import com.confeitaria.service.ProdutoService;
import com.confeitaria.service.ProdutoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProdutoController {
    private ObjectMapper mapper = new ObjectMapper();

    private ProdutoService service = new ProdutoServiceImpl();

    // POST /produtos (Cadastrar doce/bolo)
    public String cadastrarProduto(String json) {
        try {
            Produto prod = mapper.readValue(json, Produto.class);
            // agr salvando o produto criado
            service.salvar(prod);
            return "{\"status\": \"Produto " + prod.getNome() + " cadastrado!\"}";
        } catch (Exception e) {
            return "{\"erro\": \"Erro ao processar produto\"}";
        }
    }

    // GET /produtos (Listar todos)
    public String listarProdutos() {

            try {
                // AJUSTE retornando a lista atraves do service
                return mapper.writeValueAsString(service.listarTodos());
            } catch (Exception e) {
                return "[]";
        }
    }
}