import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            String linhaServidor;

            while ((linhaServidor = in.readLine()) != null) {
                System.out.println(linhaServidor);

                if (linhaServidor.endsWith(":") || linhaServidor.endsWith("?") || linhaServidor.contains("Escolha")) {
                    String resposta = scanner.nextLine();
                    out.println(resposta);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }
}
