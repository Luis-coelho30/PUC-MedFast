package server.controller;

import server.model.Farmacia;
import server.model.Pedido;
import server.model.Remedio;
import server.service.AuthService;
import server.service.PedidoService;
import server.types.StatusPedidoEnum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmaciaController {

    private final PrintWriter out;
    private final AuthService authService;
    private final PedidoService pedidoService;
    private Farmacia farmaciaLogada;

    public FarmaciaController(PrintWriter out, AuthService authService, PedidoService pedidoService) {
        this.out = out;
        this.authService = authService;
        this.pedidoService = pedidoService;
    }

    public boolean login(String nomeFarmacia, String enderecoFarmacia) {
        Farmacia f = authService.autenticarFarmacia(nomeFarmacia, enderecoFarmacia);
        if (f != null) {
            farmaciaLogada = f;
            out.println("Login da farmácia bem-sucedido!");
            return true;
        }

        out.println("Farmácia não encontrada!");
        return false;
    }

    public void listarPedidosPendentes() {
        if (farmaciaLogada == null) {
            out.println("Farmácia não autenticada!");
        } else {
            StringBuilder sb = new StringBuilder("=== Meus Pedidos ===\n");
            for (Pedido p : pedidoService.listarPedidosPorFarmacia(farmaciaLogada.getNome(), farmaciaLogada.getEndereco())) {
                sb.append(p).append("\n");
            }
            out.println(sb);
        }
    }

    public void entregarPedido(int pedidoId) {
        if (farmaciaLogada == null) {
            out.println("Farmácia não autenticada!");
        }

        Optional<Pedido> pedidoOpt = pedidoService.listarPedidoPorId(pedidoId);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            if (pedido.getStatus() == StatusPedidoEnum.CONFIRMADA) {
                pedidoService.atualizarStatusPedido(pedidoId, StatusPedidoEnum.ENTREGUE);
                out.println("Pedido marcado como entregue!");
            } else {
                out.println("O pedido ainda não foi confirmado ou já foi entregue!");
            }
        } else {
            out.println("Pedido não encontrado!");
        }
    }

    public void processarPedido(int pedidoId) {
        if (farmaciaLogada == null) {
            out.println("Farmácia não autenticada!");
            return;
        }

        Optional<Pedido> pedidoOpt = pedidoService.listarPedidoPorId(pedidoId);
        if (pedidoOpt.isEmpty()) {
            out.println("Pedido não encontrado");
            return;
        }

        Pedido pedido = pedidoOpt.get();
        Farmacia farmacia = pedido.getFarmacia();

        List<Remedio> remediosDisponiveis = new ArrayList<>();

        for (Remedio r : pedido.getListaDeRemedios()) {
            if (farmacia.diminuirEstoque(r.getNome(), r.getEstoque()) != -1) {
                r.setPreco(farmacia.getPrecoRemedioByName(r.getNome())); //Adiciona o preco do remedio pela farmacia
                remediosDisponiveis.add(r); //Adiciona o remedio na lista de disponiveis
            } else {
                out.println("Remédio " + r.getNome() + " não está disponível!");
            }
        }

        if (remediosDisponiveis.isEmpty()) {
            out.println("Nenhum remédio disponível, pedido cancelado.");
            pedidoService.atualizarStatusPedido(pedidoId, StatusPedidoEnum.CANCELADO);
            return;
        }

        pedido.setListaDeRemedios(remediosDisponiveis);
        pedidoService.atualizarStatusPedido(pedidoId, StatusPedidoEnum.CONFIRMADA);

        out.println("Pedido concluído");
    }
}