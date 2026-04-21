package com.confeitaria.service;

import com.confeitaria.model.Produto;
import java.util.List;

public interface ProdutoService {
    void salvar(Produto produto);
    List<Produto> listarTodos();
    Produto buscarPorId(int id);
}