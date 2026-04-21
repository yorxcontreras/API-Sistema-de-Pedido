package com.confeitaria;

import com.confeitaria.controller.PagamentoController;
import com.confeitaria.controller.PedidoController;
import com.confeitaria.controller.ProdutoController;

public class Main {

    public static void main(String[] args) {
        ProdutoController prodCtrl = new ProdutoController();
        PedidoController pedCtrl = new PedidoController();
        PagamentoController pagCtrl = new PagamentoController();

        System.out.println("=== TESTE INTEGRADO: MÚLTIPLOS PRODUTOS E SOMA ===");

        // 1. POPULANDO O SISTEMA (Estoque)
        System.out.println("\n[1] Cadastrando produtos...");
        prodCtrl.cadastrarProduto("{\"nome\":\"Bolo de Pote\", \"preco\":20.0}");
        prodCtrl.cadastrarProduto("{\"nome\":\"Brigadeiro\", \"preco\":5.0}");
        prodCtrl.cadastrarProduto("{\"nome\":\"Suco de Laranja\", \"preco\":10.0}");

        // 2. CRIANDO PEDIDO COM VÁRIOS ITENS
        // A soma esperada aqui é 35.0
        System.out.println("\n[2] Criando Pedido com 3 itens...");
        String pedidoJson = "{" +
                "\"status\": \"CRIADO\"," +
                "\"itens\": [" +
                "{\"nome\": \"Bolo de Pote\", \"preco\": 20.0}," +
                "{\"nome\": \"Brigadeiro\", \"preco\": 5.0}," +
                "{\"nome\": \"Suco de Laranja\", \"preco\": 10.0}" +
                "]" +
                "}";

        String responsePedido = pedCtrl.postPedido(pedidoJson);
        System.out.println("Resposta do Pedido (Soma deve ser 35.0):");
        System.out.println(responsePedido);

        // 3. FLUXO DE PAGAMENTO
        System.out.println("\n[3] Pagando o Pedido ID 1...");
        System.out.println(pagCtrl.confirmarPagamento(1));

        // 4. VERIFICAÇÃO DE BUSCA
        System.out.println("\n[4] Consultando Pedido 1 após pagamento...");
        System.out.println(pedCtrl.getPedido(1));

        // 5. REGRA DE SEGURANÇA
        System.out.println("\n[5] Tentando deletar o pedido pago...");
        System.out.println(pedCtrl.deletePedido(1));

        System.out.println("\n=== FIM DO TESTE ===");
    }
}