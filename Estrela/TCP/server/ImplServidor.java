package Estrela.TCP.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Anel.TCP.Mensagem;

public class ImplServidor implements Runnable {
    private static Map<Integer, Socket> clientes = new HashMap<>();
    public Socket socketCliente;
    public static int cont = 0;
    private boolean conexao = true;
    private ObjectInputStream entradaObjeto;

    public ImplServidor(Socket cliente) {
        socketCliente = cliente;
    }

    public void run() {
        Mensagem mensagemRecebida;
        System.out.println("Conexão " +
                ImplServidor.cont +
                " com o cliente " +
                socketCliente.getInetAddress().getHostAddress() +
                "/" +
                socketCliente.getInetAddress().getHostName());
        try {
            // Exibe mensagem no console
            entradaObjeto = new ObjectInputStream(socketCliente.getInputStream());
            while (conexao) {
                try {
                    mensagemRecebida = (Mensagem) entradaObjeto.readObject();
                    if (mensagemRecebida.getMessage().equalsIgnoreCase("fim"))
                        conexao = false;
                    else
                        System.out.println(mensagemRecebida);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            // Finaliza scanner e socket
            entradaObjeto.close();
            System.out.println("Fim do cliente " +
                    socketCliente.getInetAddress().getHostAddress());
            socketCliente.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
