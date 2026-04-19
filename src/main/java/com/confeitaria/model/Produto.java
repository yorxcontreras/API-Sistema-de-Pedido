package com.confeitaria.model;

public class Produto {
    public int id;
    public String nome;
    public double preco;
    public Produto() {} // Necessário para o Jackson
    public String getNome() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNome'");
    }
}