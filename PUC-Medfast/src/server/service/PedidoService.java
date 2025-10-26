package server.service;

import server.model.Farmacia;
import server.model.Pedido;
import server.model.Remedio;
import server.model.Usuario;
import server.repository.PedidoRepository;
import server.types.StatusPedidoEnum;

import java.util.List;
import java.util.Optional;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Usuario usuario, Farmacia farmacia, List<Remedio> remedios) {
        return pedidoRepository.save(new Pedido(usuario, farmacia, remedios));
    }

    public boolean apagarPedido(int id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.filter(pedidoRepository::delete).isPresent();
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPedidosPorUsuario(String nome, String login) {
        return pedidoRepository.findByUser(nome, login);
    }

    public List<Pedido> listarPedidosPorFarmacia(String nome, String endereco) {
        return pedidoRepository.findByFarmacy(nome, endereco);
    }

    public void marcarComoConfirmada(int pedidoId) {
        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);

        pedido.ifPresent(value -> value.setStatus(StatusPedidoEnum.CONFIRMADA));
    }

    public void marcarComoEntregue(int pedidoId) {
        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);

        pedido.ifPresent(value -> value.setStatus(StatusPedidoEnum.ENTREGUE));
    }
}
