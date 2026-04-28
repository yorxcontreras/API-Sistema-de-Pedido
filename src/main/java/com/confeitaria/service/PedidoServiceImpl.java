package com.confeitaria.service;

import com.confeitaria.model.Pedido;
import com.confeitaria.model.Produto;
import com.confeitaria.repository.PedidoRepository;
import com.confeitaria.repository.ProdutoRepository;

public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository = new PedidoRepository();

    private ProdutoRepository produtoRepository = new ProdutoRepository();

    @Override
    public Pedido salvar(Pedido pedido) {

        // validação: pedido não pode ser vazio
        if (pedido.itens == null || pedido.itens.isEmpty()) {
            throw new RuntimeException("Pedido não pode ser vazio");
        }

        double soma = 0.0;


            for (Produto p : pedido.itens) {

                Produto produtoBanco = produtoRepository.buscarPorId(p.getId());

                if (produtoBanco == null) {
                    throw new RuntimeException("Produto ID " + p.getId() + " não encontrado");
                }

                soma += produtoBanco.getPreco();
            }


        pedido.valorTotal = soma;
        pedido.status = "CRIADO";

        return pedidoRepository.salvar(pedido);
    }

    @Override
    public Pedido buscarPorId(int id) {
        Pedido pedido = pedidoRepository.buscarPorId(id);

        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado");
        }

        return pedido;
    }

    @Override
    public void deletar(int id) {
        Pedido pedido = buscarPorId(id);

        if ("PAGO".equals(pedido.status)) {
            throw new RuntimeException("Pedido já pago não pode ser deletado");
        }

        // 🔥 deletando no banco
        pedidoRepository.deletar(id);
    }

    @Override
    public void atualizarStatus(int id) {

        Pedido pedido = buscarPorId(id);

        if (!"CRIADO".equals(pedido.status)) {
            throw new RuntimeException("Pedido não pode ser pago");
        }

        if (pedido.valorTotal <= 0) {
            throw new RuntimeException("Pedido inválido para pagamento");
        }

        // 🔥 atualizando no banco
        pedidoRepository.atualizarStatus(id, "PAGO");
    }
}