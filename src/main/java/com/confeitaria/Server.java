package com.confeitaria;

import com.confeitaria.controller.PagamentoController;
import com.confeitaria.controller.PedidoController;
import com.confeitaria.controller.ProdutoController;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {

        ProdutoController produtoController = new ProdutoController();
        PedidoController pedidoController = new PedidoController();
        PagamentoController pagamentoController = new PagamentoController();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        System.out.println("Servidor rodando em http://localhost:8080");

        // ================= PRODUTOS =================
        server.createContext("/produtos", exchange -> {

            String method = exchange.getRequestMethod();
            String response = "";

            if (method.equals("POST")) {
                String body = readBody(exchange);
                response = produtoController.cadastrarProduto(body);
            }

            if (method.equals("GET")) {

                String query = exchange.getRequestURI().getQuery();

                if (query != null && query.contains("id=")) {
                    int id = Integer.parseInt(query.split("=")[1]);
                    response = produtoController.buscarProduto(id);
                } else {
                    response = produtoController.listarProdutos();
                }
            }
            if (method.equals("PUT")) {
                String body = readBody(exchange);
                response = produtoController.atualizarProduto(body);
            }

            if (method.equals("DELETEe")) {
                int id = Integer.parseInt(exchange.getRequestURI().getQuery().split("=")[1]);
                response = produtoController.deletarProduto(id);
            }

            sendResponse(exchange, response);
        });

        // ================= PEDIDOS =================
        server.createContext("/pedidos", exchange -> {

            String method = exchange.getRequestMethod();
            String response = "";

            if (method.equals("POST")) {
                String body = readBody(exchange);
                response = pedidoController.postPedido(body);
            }

            sendResponse(exchange, response);
        });

        // GET /pedidos?id=1
        server.createContext("/pedidos/buscar", exchange -> {

            int id = Integer.parseInt(exchange.getRequestURI().getQuery().split("=")[1]);
            String response = pedidoController.getPedido(id);

            sendResponse(exchange, response);
        });

        // DELETE /pedidos?id=1
        server.createContext("/pedidos/deletar", exchange -> {

            int id = Integer.parseInt(exchange.getRequestURI().getQuery().split("=")[1]);
            String response = pedidoController.deletePedido(id);

            sendResponse(exchange, response);
        });

        // ================= PAGAMENTO =================
        server.createContext("/pagamentos", exchange -> {

            int id = Integer.parseInt(exchange.getRequestURI().getQuery().split("=")[1]);
            String response = pagamentoController.confirmarPagamento(id);

            sendResponse(exchange, response);
        });

        server.start();
    }

    // ler body JSON
    private static String readBody(HttpExchange exchange) throws IOException {
        Scanner scanner = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    // enviar resposta
    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}