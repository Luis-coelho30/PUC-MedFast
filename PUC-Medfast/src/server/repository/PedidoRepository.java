package server.repository;

import server.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoRepository {


    private List<Pedido> pedidoRepository;

    public PedidoRepository() {
        pedidoRepository = new ArrayList<>();
    }

    public List<Pedido> findAll() {
        return pedidoRepository;
    }

    public Optional<Pedido> findById(int id) {
        return pedidoRepository.stream()
                .filter(p -> p.getPedidoId() == id)
                .findFirst();
    }

    public List<Pedido> findByFarmacy(String name, String endereco) {
        return pedidoRepository.stream()
                .filter(p -> p.getFarmacia().getNome().equalsIgnoreCase(name) &&
                        p.getFarmacia().getEndereco().equalsIgnoreCase(endereco))
                .toList();
    }

    public List<Pedido> findByUser(String name, String login) {
        return pedidoRepository.stream()
                .filter(p -> p.getUsuario().getNome().equalsIgnoreCase(name) &&
                        p.getUsuario().getLogin().equalsIgnoreCase(login))
                .toList();
    }

    public Pedido save(Pedido pedido) {
        pedidoRepository.add(pedido);
        return pedido;
    }

    public boolean delete(Pedido pedido) {
        return pedidoRepository.remove(pedido);
    }
}
