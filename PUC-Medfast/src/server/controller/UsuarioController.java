package server.controller;

import server.model.Usuario;
import server.model.Farmacia;
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

    public void criarPedido(Farmacia farmacia, List<Remedio> remedios) {
        if (this.usuarioLogado == null) {
            System.out.println("Usuário não autenticado!");
        } else {
            pedidoService.criarPedido(this.usuarioLogado, farmacia, remedios);
            System.out.println("Pedido criado com sucesso!");
        }
    }

    public void listarMeusPedidos() {
        if (this.usuarioLogado == null) {
            System.out.println("Usuário não autenticado!");
        } else {
            List<?> pedidos = pedidoService.listarPedidosPorUsuario(this.usuarioLogado.getNome(), this.usuarioLogado.getLogin());
            pedidos.forEach(System.out::println);
        }
    }
}
