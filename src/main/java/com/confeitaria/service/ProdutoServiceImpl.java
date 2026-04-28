package com.confeitaria.service;

import com.confeitaria.model.Produto;
import com.confeitaria.repository.ProdutoRepository;

import java.util.List;

public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository produtoRepository = new ProdutoRepository();

    @Override
    public void salvar(Produto produto) {

        // validação básica
        if (produto == null) {
            throw new RuntimeException("Produto inválido");
        }

        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }

        if (produto.getPreco() <= 0) {
            throw new RuntimeException("Preço do produto inválido");
        }

        // salva no banco
        produtoRepository.salvar(produto);
    }

    public void atualizar(Produto produto) {
        produtoRepository.atualizar(produto);
    }
    @Override
    public List<Produto> listarTodos() {
        return produtoRepository.listarTodos();
    }

    @Override
    public Produto buscarPorId(int id) {

        Produto produto = produtoRepository.buscarPorId(id);

        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        return produto;
    }

    @Override
    public void deletar(int id) {

        Produto produto = buscarPorId(id);

        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        produtoRepository.deletar(id);
    }
}