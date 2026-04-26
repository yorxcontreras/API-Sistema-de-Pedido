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
}