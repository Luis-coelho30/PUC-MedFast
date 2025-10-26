package server.repository;

import server.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {


    private List<Pedido> pedidoRepository;

    public PedidoRepository() {
        pedidoRepository = new ArrayList<>();
    }

    public List<Pedido> findAll() {
        return pedidoRepository;
    }

    public List<Pedido> findByFarmacyName(String name) {
        return pedidoRepository.stream()
                .filter(p -> p.getFarmacia().getNome().equalsIgnoreCase(name))
                .toList();
    }

    public List<Pedido> findByFarmacyAddress(String address) {
        return pedidoRepository.stream()
                .filter(p -> p.getFarmacia().getEndereco().equalsIgnoreCase(address))
                .toList();
    }

    public void save(Pedido pedido) {
        pedidoRepository.add(pedido);
    }

    public void delete(Pedido pedido) {
        pedidoRepository.remove(pedido);
    }
}
