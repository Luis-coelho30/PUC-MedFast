package server.controller;

import server.model.Farmacia;
import server.service.AuthService;
import server.service.PedidoService;

import java.util.List;

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

    public void listarPedidosPendentes() {
        if (farmaciaLogada == null) {
            System.out.println("Farmácia não autenticada!");
        } else {
            List<?> pedidos = pedidoService.listarPedidosPorFarmacia(farmaciaLogada.getNome(), farmaciaLogada.getEndereco());
            pedidos.forEach(System.out::println);
        }
    }

    public void confirmarPedido(int pedidoId) {
        if (farmaciaLogada == null) {
            System.out.println("Farmácia não autenticada!");
        } else {
            pedidoService.marcarComoConfirmada(pedidoId);
            System.out.println("Pedido confirmado!");
        }
    }

    public void entregarPedido(int pedidoId) {
        if (farmaciaLogada == null) {
            System.out.println("Farmácia não autenticada!");
        } else {
            pedidoService.marcarComoEntregue(pedidoId);
            System.out.println("Pedido marcado como entregue!");
        }
    }
}