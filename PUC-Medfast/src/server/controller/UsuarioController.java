package server.controller;

import server.model.Pedido;
import server.model.Usuario;
import server.model.Remedio;
import server.service.AuthService;
import server.service.PedidoService;

import java.io.PrintWriter;
import java.util.List;

public class UsuarioController {

    private final PrintWriter out;
    private final AuthService authService;
    private final PedidoService pedidoService;
    private Usuario usuarioLogado;

    public UsuarioController(PrintWriter out, AuthService authService, PedidoService pedidoService) {
        this.out = out;
        this.authService = authService;
        this.pedidoService = pedidoService;
    }

    public boolean login(String login, String senha) {
        Usuario user = authService.autenticarUsuario(login, senha);
        if (user != null) {
            this.usuarioLogado = user;
            out.println("Login bem-sucedido!");
            return true;
        }

        out.println("Login ou senha incorretos!");
        return false;
    }

    public void criarPedido(String nomeFarmacia, String enderecoFarmacia, List<Remedio> remedios) {
        if (this.usuarioLogado == null) {
            out.println("Usuário não autenticado!");
        } else {
            if (pedidoService.criarPedido(this.usuarioLogado, nomeFarmacia, enderecoFarmacia, remedios)) {
                out.println("Pedido criado com sucesso!");
            }
        }
    }

    public void listarMeusPedidos() {
        if (this.usuarioLogado == null) {
            out.println("Usuário não autenticado!");
        } else {
            StringBuilder sb = new StringBuilder("=== Meus Pedidos ===\n");
            for (Pedido p : pedidoService.listarPedidosPorUsuario(usuarioLogado.getNome(), usuarioLogado.getLogin())) {
                sb.append(p).append("\n");
            }
            out.println(sb);
        }
    }
}
