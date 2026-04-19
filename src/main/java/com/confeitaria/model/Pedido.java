package com.confeitaria.model;
import java.util.List;

public class Pedido {
    public int id;
    public String status;
    public List<Produto> itens;
    public double valorTotal;
    public Pedido() {} // Necessário para o Jackson
}