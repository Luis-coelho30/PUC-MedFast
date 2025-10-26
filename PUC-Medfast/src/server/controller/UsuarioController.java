package server.controller;

import server.model.Pedido;
import server.model.Usuario;
import server.model.Remedio;
import server.service.AuthService;
import server.service.PedidoService;

import java.util.List;

public class UsuarioController {

    private final AuthService authService;
    private final PedidoService pedidoService;
    private Usuario usuarioLogado;

    public UsuarioController(AuthService authService, PedidoService pedidoService) {
        this.authService = authService;
        this.pedidoService = pedidoService;
    }

    public boolean login(String login, String senha) {
        Usuario user = authService.autenticarUsuario(login, senha);
        if (user != null) {
            this.usuarioLogado = user;
            System.out.println("Login bem-sucedido!");
            return true;
        }

        System.out.println("Login ou senha incorretos!");
        return false;
    }

    public void criarPedido(String nomeFarmacia, String enderecoFarmacia, List<Remedio> remedios) {
        if (this.usuarioLogado == null) {
            System.out.println("Usuário não autenticado!");
        } else {
            if (pedidoService.criarPedido(this.usuarioLogado, nomeFarmacia, enderecoFarmacia, remedios)) {
                System.out.println("Pedido criado com sucesso!");
            }
        }
    }

    public String listarMeusPedidos() {
        if (this.usuarioLogado == null) {
            return "Usuário não autenticado!";
        } else {
            StringBuilder sb = new StringBuilder("=== Meus Pedidos ===\n");
            for (Pedido p : pedidoService.listarPedidosPorUsuario(usuarioLogado.getNome(), usuarioLogado.getLogin())) {
                sb.append(p).append("\n");
            }
            return sb.toString();
        }
    }
}
