package com.confeitaria.service;

import com.confeitaria.model.Pedido;
import com.confeitaria.model.Produto;

import java.util.HashMap;
import java.util.Map;

// regras de negocio da aplicação

public class PedidoServiceImpl  implements PedidoService{

    // usando hashmap pra simular o banco em memoria
    private static Map<Integer, Pedido> pedidos = new HashMap<>();

    // contador pra gerar o id
    private static int contador= 1;


    @Override
    public Pedido salvar (Pedido pedido){
        //inicia o id
        pedido.id = contador++;

        // uma variavel pra valor total
        double soma = 0.0;

        // validação se a lista de itens nao esta vazia
        if (pedido.itens != null) {
            for (Produto p : pedido.itens) {
                soma += p.preco; // Soma o preço de cada produto
            }
        }
        // atribuindo soma a valor total
        pedido.valorTotal = soma;

        // inicia os pedidos com status Criado
        pedido.status = "CRIADO";

        // salva por enquanto no hashmap (futuro no banco)
        pedidos.put(pedido.id ,pedido);
        return pedido;
    }



    @Override
    public Pedido buscarPorId(int id){
        Pedido pedido = pedidos.get(id);

        // valida o pedido
        if (pedido == null){
            throw new RuntimeException("Pedido não foi encontrado");
        }
        return pedido;
    }

    @Override
    public void deletar(int id) {
        Pedido pedido = buscarPorId(id);

        // valida pedido pago não pode ser deletado

        if ("PAGO".equals(pedido.status)) {
            throw new RuntimeException("Pagamento já foi efetuado , então pedido não pode ser deletado");
        }
        pedidos.remove(id);

    }

    @Override
    public void atualizarStatus(int id){

        Pedido pedido = buscarPorId(id);

        if (!"CRIADO".equals(pedido.status)){
            throw new RuntimeException("Nao foi possivel o pagamento , o pedido não foi criado");
        }
        pedido.status = "PAGO";
    }


}
