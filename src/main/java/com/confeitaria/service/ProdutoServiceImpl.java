package com.confeitaria.service;

import com.confeitaria.model.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoServiceImpl implements ProdutoService {

    // O static para que produtos cadastrados não sumam
    private static Map<Integer, Produto> produtos = new HashMap<>();
    private static int contador = 1;

    @Override
    public void salvar(Produto produto) {
        produto.setId(contador++); //setId dita que cada produto tenha um ID único
        produtos.put(produto.getId(), produto);
    }

    @Override
    public List<Produto> listarTodos() {
        // Transforma o Map em uma lista para o Controller mostrar no JSON
        return new ArrayList<>(produtos.values());
    }

    @Override
    public Produto buscarPorId(int id) {
        Produto produto = produtos.get(id);

        // validação de produto criado
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado!");
        }
        return produto;
    }
}