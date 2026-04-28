package com.confeitaria.repository;

import com.confeitaria.model.Pedido;
import com.confeitaria.model.Produto;
import java.sql.*;

public class PedidoRepository {

    public Pedido salvar(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedido (status, valor_total) VALUES (?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Inicia uma transação
            conn.setAutoCommit(false); 
            
            try (PreparedStatement stmt = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, pedido.status);
                stmt.setDouble(2, pedido.valorTotal);
                stmt.executeUpdate();
                
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    pedido.id = rs.getInt(1);
                }
                
                // Salvar os itens na tabela intermediária
                if (pedido.itens != null) {
                    String sqlItem = "INSERT INTO pedido_item (pedido_id, produto_id) VALUES (?, ?)";
                    try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                        for (Produto p : pedido.itens) {
                            stmtItem.setInt(1, pedido.id);
                            stmtItem.setInt(2, p.getId());
                            stmtItem.executeUpdate();
                        }
                    }
                }
                // Confirma a transação
                conn.commit(); 
                return pedido;
                
            } catch (SQLException e) {
                conn.rollback(); // Se der erro, desfaz tudo
                throw new RuntimeException("Erro ao salvar pedido, feito rollback.", e);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro de conexão no pedido", e);
        }
    }

    public void atualizarStatus(int id, String novoStatus) {
        String sql = "UPDATE pedido SET status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, novoStatus);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status do pedido", e);
        }
    }

    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.id = rs.getInt("id");
                pedido.status = rs.getString("status");
                pedido.valorTotal = rs.getDouble("valor_total");

                return pedido;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido", e);
        }

        return null;
    }
    public void deletar(int id) {
        String sqlItens = "DELETE FROM pedido_item WHERE pedido_id = ?";
        String sqlPedido = "DELETE FROM pedido WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {

            conn.setAutoCommit(false);

            try (
                    PreparedStatement stmtItens = conn.prepareStatement(sqlItens);
                    PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido)
            ) {
                // primeiro remove os itens
                stmtItens.setInt(1, id);
                stmtItens.executeUpdate();

                // depois remove o pedido
                stmtPedido.setInt(1, id);
                stmtPedido.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Erro ao deletar pedido", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro de conexão ao deletar", e);
        }
    }
}