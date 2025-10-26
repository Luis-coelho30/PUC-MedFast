package server.service;

import server.model.Farmacia;
import server.model.Pedido;
import server.model.Remedio;
import server.model.Usuario;
import server.repository.FarmaciaRepository;
import server.repository.PedidoRepository;
import server.types.StatusPedidoEnum;

import java.util.List;
import java.util.Optional;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final FarmaciaRepository farmaciaRepository;

    public PedidoService(PedidoRepository pedidoRepository, FarmaciaRepository farmaciaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.farmaciaRepository = farmaciaRepository;
    }

    public boolean criarPedido(Usuario usuario, String nomeFarmacia, String enderecoFarmacia, List<Remedio> remedios) {
        Optional<Farmacia> farmaciaOpt = farmaciaRepository.findByNameAndAddress(nomeFarmacia, enderecoFarmacia);

        if(farmaciaOpt.isPresent()) {
            pedidoRepository.save(new Pedido(usuario, farmaciaOpt.get(), remedios));
            return true;
        }

        return false;
    }

    public boolean apagarPedido(int id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.filter(pedidoRepository::delete).isPresent();
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
    public Optional<Pedido> listarPedidoPorId(int id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> listarPedidosPorUsuario(String nome, String login) {
        return pedidoRepository.findByUser(nome, login);
    }

    public List<Pedido> listarPedidosPorFarmacia(String nome, String endereco) {
        return pedidoRepository.findByFarmacy(nome, endereco);
    }

    public void atualizarStatusPedido(int pedidoId, StatusPedidoEnum status) {
        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);

        pedido.ifPresent(value -> value.setStatus(status));
    }
}
