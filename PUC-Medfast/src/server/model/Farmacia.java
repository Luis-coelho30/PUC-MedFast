package server.model;

import java.util.ArrayList;
import java.util.List;

public class Farmacia {

    private String nome;
    private String endereco;
    private List<Remedio> catalogo;

    public Farmacia(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.catalogo = new ArrayList<>();
    }

    public int aumentarEstoque(String nomeRemedio, int quantidade) {
        Remedio remedio = catalogo.stream()
                            .filter(r -> r.getNome().equalsIgnoreCase(nomeRemedio))
                            .findFirst()
                            .orElse(null);

        if(remedio != null) {
            return remedio.aumentarEstoque(quantidade);
        }

        return 0;
    }

    public int diminuirEstoque(String nomeRemedio, int quantidade) {
        Remedio remedio = catalogo.stream()
                .filter(r -> r.getNome().equalsIgnoreCase(nomeRemedio))
                .findFirst()
                .orElse(null);

        if(remedio != null && remedio.getEstoque() >= quantidade) {
            return remedio.diminuirEstoque(quantidade);
        }

        return -1;
    }

    public double getPrecoRemedioByName(String nomeRemedio) {
        Remedio remedio = catalogo.stream()
                .filter(r -> r.getNome().equalsIgnoreCase(nomeRemedio))
                .findFirst()
                .orElse(null);

        if(remedio != null) {
            return remedio.getPreco();
        }

        return -1;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Remedio> getCatalogo() {
        return catalogo;
    }

    public void adicionarRemedio(Remedio remedio) {
        this.catalogo.add(remedio);
    }

    @Override
    public String toString() {
        return "Farmacia{" +
                "nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", catalogo=" + catalogo +
                "}\n";
    }
}