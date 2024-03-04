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
                System.out.println("HostAddress = " + inet.getHostAddress());
                System.out.println("HostName = " + inet.getHostName());
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
}
