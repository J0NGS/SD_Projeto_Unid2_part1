package Anel.TCP.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

// Classe responsável por representar o cliente no sistema
public class Cliente implements Runnable {
    String ip; // Endereço IP do servidor
    int porta; // Porta do servidor
    int idProcesso; // ID do processo do cliente

    // Construtor que recebe o endereço IP, a porta e o ID do processo do cliente
    public Cliente(String ip, int porta, int idProcesso) {
        this.ip = ip;
        this.porta = porta;
        this.idProcesso = idProcesso;
    }

    @Override
    // Método run que define o comportamento do cliente
    public void run() {
        // Loop infinito para tentar se conectar ao servidor
        while (true) {
            try {
                // Cria um socket para se conectar ao servidor com o endereço IP e a porta
                // fornecidos
                Socket socket = new Socket(ip, porta);
                InetAddress inet = socket.getInetAddress(); // Obtém informações sobre o endereço IP do servidor
                // Exibe uma mensagem indicando que a conexão foi bem-sucedida
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
                System.out.println("LOG_CLIENT-> HostAddress = " + inet.getHostAddress()); // Exibe o endereço IP do
                                                                                           // servidor
                System.out.println("LOG_CLIENT-> HostName = " + inet.getHostName()); // Exibe o nome do host do servidor
                System.out.println("===============================");
                // Cria uma instância da classe ImplCliente para tratar a comunicação com o
                // servidor
                ImplCliente c = new ImplCliente(socket, idProcesso);
                Thread t = new Thread(c); // Cria uma nova thread para o cliente
                t.start(); // Inicia a thread do cliente para se comunicar com o servidor
                break; // Conexão bem-sucedida, saia do loop
            } catch (IOException e) {
                // Se a conexão falhar, aguarda um tempo antes de tentar novamente
                try {
                    Thread.sleep(1000); // Aguarda 1 segundo antes de tentar se reconectar
                } catch (InterruptedException ex) {
                    ex.printStackTrace(); // Trata exceções de interrupção
                }
            }
        }
    }

    // Métodos getters e setters para os atributos da classe
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
