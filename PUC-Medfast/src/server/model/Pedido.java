package server.model;

import server.types.StatusPedidoEnum;

import java.util.List;

public class Pedido {

    private Usuario usuario;
    private Farmacia farmacia;
    private List<Remedio> listaDeRemedios;
    private StatusPedidoEnum status;

    public Pedido(Usuario usuario, Farmacia farmacia, List<Remedio> listaDeRemedios, StatusPedidoEnum status) {
        this.usuario = usuario;
        this.farmacia = farmacia;
        this.listaDeRemedios = listaDeRemedios;
        this.status = status;
    }

    public double getvValorTotal() {
        return listaDeRemedios.stream()
                .mapToDouble(Remedio::getPreco)
                .sum();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public List<Remedio> getListaDeRemedios() {
        return listaDeRemedios;
    }

    public void setListaDeRemedios(List<Remedio> listaDeRemedios) {
        this.listaDeRemedios = listaDeRemedios;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "usuario = " + usuario +
                ", farmacia = " + farmacia +
                ", Lista de remedios = " + listaDeRemedios +
                ", Preco total = " + getvValorTotal() +
                ", status = " + status +
                '}';
    }
}