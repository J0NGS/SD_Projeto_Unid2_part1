package Anel.TCP.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Anel.TCP.client.Cliente;

public class Servidor implements Runnable {
    ServerSocket socketServidor;
    Socket cliente;
    int porta;
    int idProcesso;
    Cliente clienteApp;

    public Servidor(int porta, int idProcesso, Cliente clienteApp) {
        this.porta = porta;
        this.idProcesso = idProcesso;
        this.clienteApp = clienteApp;
    }

    @Override
    public void run() {
        /*
         * Cria um socket na porta 54321
         */
        try {
            socketServidor = new ServerSocket(porta);
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

            /*
             * Aguarda alguém se conectar.
             * A execução do servidor fica bloqueada na chamada
             * do método accept da classe ServerSocket.
             * Quando alguém se conectar ao
             * servidor, o método desbloqueia e
             * retorna com um objeto da classe Socket, que
             * é uma porta da comunicação.
             */
            System.out.println("LOG_SERVER-> Aguardando conexão do cliente...");
            while (true) {
                cliente = socketServidor.accept();
                // Cria uma thread do servidor para tratar a conexão
                ImplServidor servidor = new ImplServidor(cliente, idProcesso, clienteApp);
                Thread t = new Thread(servidor);
                // Inicia a thread para o cliente conectado
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
