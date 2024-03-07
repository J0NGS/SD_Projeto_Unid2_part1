package Estrela.TCP.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente{
    Socket socket;
    InetAddress inet;
    String ip;
    int porta;

    public Cliente(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
        this.run();
    }

    public void run() {
        /*
         * Para se conectar ao servidor,
         * cria-se objeto Socket.
         * O primeiro parâmetro é o
         * IP ou endereço da máquina que
         * se quer conectar e o segundo é
         * a porta da aplicação.
         * Neste caso, usa-se o IP da
         * máquina local (127.0.0.1) e a porta da
         * aplicação Servidor de Eco (54321).
         */
        try {
            socket = new Socket(ip, porta);

            inet = socket.getInetAddress();
            System.out.println("\r\n" + //
                    " _____ _ _            _                     _ _                  \r\n" + //
                    "/  __ | (_)          | |                   | (_)                 \r\n" + //
                    "| /  \\| |_  ___ _ __ | |_ ___    ___  _ __ | |_ _ __   ___       \r\n" + //
                    "| |   | | |/ _ | '_ \\| __/ _ \\  / _ \\| '_ \\| | | '_ \\ / _ \\      \r\n" + //
                    "| \\__/| | |  __| | | | ||  __/ | (_) | | | | | | | | |  __/_ _ _ \r\n" + //
                    " \\____|_|_|\\___|_| |_|\\__\\___|  \\___/|_| |_|_|_|_| |_|\\___(_(_(_)\r\n" + //
                    "                                                                 \r\n" + //
                    "                                                                 \r\n" + //
                    "");
            System.out.println("HostAddress = " + inet.getHostAddress());
            System.out.println("HostName = " + inet.getHostName());
            /*
             * Criar um novo objeto Cliente
             * com a conexão socket para que
             * seja executado em
             * um novo processo.
             * Permitindo assim a conexão de
             * vários clientes com o
             * Java
             * servidor.
             */
            ImplCliente c = new ImplCliente(socket);
            Thread t = new Thread(c);
            t.start();
        } catch (IOException e) {
            try {
                System.out.println("LOG_CLIENT-> não conseguiu se conectar ao servidor, tentando novamente...");
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
