package server.model;

public class Remedio {

    private String nome;
    private double preco;
    private int estoque;

    public Remedio(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public Remedio(String nome, int estoque) {
        this.nome = nome;
        this.estoque = estoque;
    }

    public int aumentarEstoque(int quantidade) {
        return estoque += quantidade;
    }

    public int diminuirEstoque(int quantidade) {
        return estoque -= quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return nome + " (R$" + preco + ", estoque: " + estoque + ")\n";
    }
}
