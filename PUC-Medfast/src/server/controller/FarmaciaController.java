package server.controller;

import server.model.Farmacia;
import server.model.Pedido;
import server.model.Remedio;
import server.service.AuthService;
import server.service.PedidoService;
import server.types.StatusPedidoEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmaciaController {

    private final AuthService authService;
    private final PedidoService pedidoService;
    private Farmacia farmaciaLogada;

    public FarmaciaController(AuthService authService, PedidoService pedidoService) {
        this.authService = authService;
        this.pedidoService = pedidoService;
    }

    public boolean login(String nomeFarmacia, String enderecoFarmacia) {
        Farmacia f = authService.autenticarFarmacia(nomeFarmacia, enderecoFarmacia);
        if (f != null) {
            farmaciaLogada = f;
            System.out.println("Login da farmácia bem-sucedido!");
            return true;
        }

        System.out.println("Farmácia não encontrada!");
        return false;
    }

    public String listarPedidosPendentes() {
        if (farmaciaLogada == null) {
            System.out.println("Farmácia não autenticada!");
            return "Farmácia não autenticada!";
        } else {
            StringBuilder sb = new StringBuilder("=== Meus Pedidos ===\n");
            for (Pedido p : pedidoService.listarPedidosPorFarmacia(farmaciaLogada.getNome(), farmaciaLogada.getEndereco())) {
                sb.append(p).append("\n");
            }
            return sb.toString();
        }
    }

    public void entregarPedido(int pedidoId) {
        if (farmaciaLogada == null) {
            System.out.println("Farmácia não autenticada!");
        } else {
            pedidoService.atualizarStatusPedido(pedidoId, StatusPedidoEnum.ENTREGUE);
            System.out.println("Pedido marcado como entregue!");
        }
    }

    public boolean processarPedido(int pedidoId) {
        if (farmaciaLogada == null) {
            System.out.println("Farmácia não autenticada!");
            return false;
        }

        Optional<Pedido> pedidoOpt = pedidoService.listarPedidoPorId(pedidoId);
        if (pedidoOpt.isEmpty()) {
            System.out.println("Pedido não encontrado");
            return false;
        }

        Pedido pedido = pedidoOpt.get();
        Farmacia farmacia = pedido.getFarmacia();

        List<Remedio> remediosDisponiveis = new ArrayList<>();

        for (Remedio r : pedido.getListaDeRemedios()) {
            if (farmacia.diminuirEstoque(r.getNome(), r.getEstoque()) != -1) {
                r.setPreco(farmacia.getPrecoRemedioByName(r.getNome())); //Adiciona o preco do remedio pela farmacia
                remediosDisponiveis.add(r); //Adiciona o remedio na lista de disponiveis
            } else {
                System.out.println("Remédio " + r.getNome() + " não está disponível!");
            }
        }

        if (remediosDisponiveis.isEmpty()) {
            System.out.println("Nenhum remédio disponível, pedido cancelado.");
            pedidoService.atualizarStatusPedido(pedidoId, StatusPedidoEnum.CANCELADO);
            return false;
        }

        pedido.setListaDeRemedios(remediosDisponiveis);
        pedidoService.atualizarStatusPedido(pedidoId, StatusPedidoEnum.CONFIRMADA);

        return true;
    }
}