package Anel.TCP.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente implements Runnable {
    String ip;
    int porta;
    int idProcesso;

    public Cliente(String ip, int porta, int idProcesso) {
        this.ip = ip;
        this.porta = porta;
        this.idProcesso = idProcesso;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = new Socket(ip, porta);
                InetAddress inet = socket.getInetAddress();
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
                System.out.println("===============================");
                System.out.println("LOG_CLIENT-> HostAddress = " + inet.getHostAddress());
                System.out.println("LOG_CLIENT-> HostName = " + inet.getHostName());
                System.out.println("===============================");
                ImplCliente c = new ImplCliente(socket, idProcesso);
                Thread t = new Thread(c);
                t.start();
                break; // Conexão bem-sucedida, saia do loop
            } catch (IOException e) {
                try {
                    Thread.sleep(1000); // Aguarda 1 segundo antes de tentar novamente
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return this.porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public int getIdProcesso() {
        return this.idProcesso;
    }

    public void setIdProcesso(int idProcesso) {
        this.idProcesso = idProcesso;
    }

}
