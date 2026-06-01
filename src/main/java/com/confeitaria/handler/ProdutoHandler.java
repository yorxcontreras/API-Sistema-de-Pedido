package com.confeitaria.handler;

import com.confeitaria.model.Produto;
import com.confeitaria.service.ProdutoService;
import com.confeitaria.service.ProdutoServiceImpl;

import java.util.List;

public class ProdutoHandler {

    private ProdutoService service = new ProdutoServiceImpl();

    public void salvar(Produto produto) {
        service.salvar(produto);
    }

    public void atualizar(Produto produto) {
        service.atualizar(produto);
    }

    public void deletar(int id) {
        service.deletar(id);
    }

    public List<Produto> listarTodos() {
        return service.listarTodos();
    }

    public Produto buscarPorId(int id) {
        return service.buscarPorId(id);
    }
}