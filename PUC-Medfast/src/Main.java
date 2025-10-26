import server.Server;
import server.repository.FarmaciaRepository;
import server.repository.PedidoRepository;
import server.repository.UsuarioRepository;
import server.service.AuthService;
import server.service.PedidoService;

public class Main {
    public static void main(String[] args) {
        // Cria serviços fake para teste
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        FarmaciaRepository farmaciaRepository = new FarmaciaRepository();
        PedidoRepository pedidoRepository = new PedidoRepository();

        AuthService authService = new AuthService(usuarioRepository, farmaciaRepository);
        PedidoService pedidoService = new PedidoService(pedidoRepository, farmaciaRepository);

        // Instancia o servidor na porta 12345
        Server server = new Server(12345, authService, pedidoService);

        System.out.println("Iniciando servidor de teste...");
        server.start(); // Vai travar aqui aguardando conexão do cliente
    }
}