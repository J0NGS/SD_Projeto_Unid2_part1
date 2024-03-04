package Anel.TCP.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Anel.TCP.client.Cliente;

// Classe Servidor implementa a interface Runnable para possibilitar a execução em uma thread separada
public class Servidor implements Runnable {
    ServerSocket socketServidor;
    Socket cliente;
    int porta;
    int idProcesso;
    Cliente clienteApp;

    // Construtor da classe Servidor
    public Servidor(int porta, int idProcesso, Cliente clienteApp) {
        this.porta = porta;
        this.idProcesso = idProcesso;
        this.clienteApp = clienteApp;
    }

    // Método run() é chamado quando a thread é iniciada
    @Override
    public void run() {
        try {
            // Cria um socket do servidor na porta especificada
            socketServidor = new ServerSocket(porta);
            // Exibe informações sobre o servidor
            System.out.println("\r\n" + //
                    " _____                 _     _                          _ _                  \r\n" + //
                    "/  ___|               (_)   | |                        | (_)                 \r\n" + //
                    "\\ `--.  ___ _ ____   ___  __| | ___  _ __    ___  _ __ | |_ _ __   ___       \r\n" + //
                    " `--. \\/ _ | '__\\ \\ / | |/ _` |/ _ \\| '__|  / _ \\| '_ \\| | | '_ \\ / _ \\      \r\n" + //
                    "/\\__/ |  __| |   \\ V /| | (_| | (_) | |    | (_) | | | | | | | | |  __/_ _ _ \r\n" + //
                    "\\____/ \\___|_|    \\_/ |_|\\__,_|\\___/|_|     \\___/|_| |_|_|_|_| |_|\\___(_(_(_)\r\n" + //
                    "                                                                             \r\n" + //
                    "                                                                             \r\n" + //
                    "");
            System.out.println("===============================");
            System.out.println("LOG_SERVER-> Servidor rodando na porta " +
                    socketServidor.getLocalPort());
            System.out.println("LOG_SERVER-> HostAddress = " +
                    InetAddress.getLocalHost().getHostAddress());
            System.out.println("LOG_SERVER-> HostName = " +
                    InetAddress.getLocalHost().getHostName());
            System.out.println("===============================");

            // Aguarda conexão de clientes
            System.out.println("LOG_SERVER-> Aguardando conexão do cliente...");
            while (true) {
                cliente = socketServidor.accept();
                // Cria uma instância da classe ImplServidor para tratar a conexão
                ImplServidor servidor = new ImplServidor(cliente, idProcesso, clienteApp);
                Thread t = new Thread(servidor);
                // Inicia uma thread para o cliente conectado
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
