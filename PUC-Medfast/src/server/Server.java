package server;

import server.controller.FarmaciaController;
import server.controller.UsuarioController;
import server.model.Remedio;
import server.service.AuthService;
import server.service.PedidoService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final int port;
    private final AuthService authService;
    private final PedidoService pedidoService;

    public Server(int port, AuthService authService, PedidoService pedidoService) {
        this.port = port;
        this.authService = authService;
        this.pedidoService = pedidoService;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado na porta " + port);

            while (true) {
                System.out.println("Aguardando cliente...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                handleClient(clientSocket);

                clientSocket.close();
                System.out.println("Cliente desconectado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("Digite tipo de cliente (USUARIO ou FARMACIA):");
            String tipoCliente = in.readLine().trim().toUpperCase();

            if (tipoCliente.equals("USUARIO")) {
                UsuarioController uc = new UsuarioController(authService, pedidoService);
                handleUsuario(uc, in, out);
            } else if (tipoCliente.equals("FARMACIA")) {
                FarmaciaController fc = new FarmaciaController(authService, pedidoService);
                handleFarmacia(fc, in, out);
            } else {
                out.println("Tipo de cliente inválido!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUsuario(UsuarioController uc, BufferedReader in, PrintWriter out) throws IOException {
        out.println("=== Login do Usuário ===");
        out.println("Login:");
        String login = in.readLine();
        out.println("Senha:");
        String senha = in.readLine();

        if (!uc.login(login, senha)) {
            out.println("Falha no login!");
            return;
        }

        out.println("Login bem-sucedido!\n");

        boolean executando = true;
        while (executando) {
            out.println("=== PUC-MedFast Cliente ===");
            out.println("1. Criar novo pedido");
            out.println("2. Listar meus pedidos");
            out.println("3. Sair");
            out.println("Escolha uma opção:");

            String opcao = in.readLine();
            if (opcao == null) {
                System.out.println("Cliente desconectado durante o menu do usuário.");
                break;
            }

            switch (opcao) {
                case "1" -> {
                    List<Remedio> remedios = new ArrayList<>();

                    while (true) {
                        out.println("=== Criação de Pedido ===");
                        out.println("Digite o nome do medicamento:");
                        String medicamento = in.readLine();

                        if(medicamento.equalsIgnoreCase("FIM")) {
                            break;
                        }

                        out.println("Digite a quantidade:");
                        String qtdStr = in.readLine();
                        int quantidade = Integer.parseInt(qtdStr);

                        Remedio novoRemedio = new Remedio(medicamento, quantidade);
                        remedios.add(novoRemedio);
                    }

                    out.println("Digite o nome da farmácia:");
                    String farmacia = in.readLine();

                    out.println("Digite o endereco da farmácia:");
                    String endereco = in.readLine();

                    uc.criarPedido(farmacia, endereco, remedios);
                    out.println("Pedido criado com sucesso");
                }
                case "2" -> out.println(uc.listarMeusPedidos());
                case "3" -> {
                    out.println("Saindo do menu do usuário...");
                    executando = false;
                }
                default -> out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void handleFarmacia(FarmaciaController fc, BufferedReader in, PrintWriter out) throws IOException {
        out.println("=== Login da Farmácia ===");
        out.println("Nome da farmácia:");
        String nome = in.readLine();
        out.println("Endereço da farmácia:");
        String endereco = in.readLine();

        if (!fc.login(nome, endereco)) {
            out.println("Falha no login da farmácia!");
            return;
        }

        out.println("Login bem-sucedido!\n");

        boolean executando = true;
        while (executando) {
            out.println("=== Menu da Farmácia ===");
            out.println("1. Listar pedidos pendentes");
            out.println("2. Confirmar pedido");
            out.println("3. Marcar pedido como entregue");
            out.println("4. Sair");
            out.println("Escolha uma opção:");

            String opcao = in.readLine();
            if (opcao == null) {
                System.out.println("Cliente desconectado durante o menu do usuário.");
                break;
            }

            switch (opcao) {
                case "1" -> out.println(fc.listarPedidosPendentes());
                case "2" -> {
                    out.println("Digite o ID do pedido para confirmar:");
                    int id = Integer.parseInt(in.readLine());
                    if(fc.processarPedido(id)) {
                        out.println("Pedido confirmado!");
                    } else {
                        out.println("Pedido não pôde ser concluído");
                    }
                }
                case "3" -> {
                    out.println("Digite o ID do pedido para marcar como entregue:");
                    int id = Integer.parseInt(in.readLine());
                    fc.entregarPedido(id);
                    out.println("Pedido marcado como entregue!");
                }
                case "4" -> {
                    out.println("Saindo do menu da farmácia...");
                    executando = false;
                }
                default -> out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
