import server.Server;
import server.model.Farmacia;
import server.model.Remedio;
import server.model.Usuario;
import server.repository.FarmaciaRepository;
import server.repository.PedidoRepository;
import server.repository.UsuarioRepository;
import server.service.AuthService;
import server.service.PedidoService;

public class Main {
    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        FarmaciaRepository farmaciaRepository = new FarmaciaRepository();
        PedidoRepository pedidoRepository = new PedidoRepository();

        // Criando usuários de teste
        usuarioRepository.save(new Usuario("joao", "joao@email.com", "1234"));
        usuarioRepository.save(new Usuario("maria", "maria@email.com", "abcd"));

        // Criando farmácias de teste
        Farmacia farm1 = new Farmacia("Farmacia Central", "Rua A, 123");
        farm1.adicionarRemedio(new Remedio("Dipirona", 50, 50));
        farm1.adicionarRemedio(new Remedio("Paracetamol", 100, 100));

        Farmacia farm2 = new Farmacia("Farmacia BoaSaude", "Av. B, 456");
        farm2.adicionarRemedio(new Remedio("Ibuprofeno", 75, 75));
        farm2.adicionarRemedio(new Remedio("Amoxicilina", 30, 30));

        farmaciaRepository.save(farm1);
        farmaciaRepository.save(farm2);

        AuthService authService = new AuthService(usuarioRepository, farmaciaRepository);
        PedidoService pedidoService = new PedidoService(pedidoRepository, farmaciaRepository);

        // Instancia o servidor na porta 12345
        Server server = new Server(12345, authService, pedidoService);

        System.out.println("Iniciando servidor de teste...");
        server.start();
    }
}