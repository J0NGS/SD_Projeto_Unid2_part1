package Anel.TCP.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import Anel.TCP.Mensagem;

public class ImplServidor implements Runnable {
    public Socket socketCliente;
    private boolean conexao = true;
    private ObjectInputStream entrada;
    private int idProcesso;
    public ImplServidor(Socket cliente, int idProcesso) {
        socketCliente = cliente;
        this.idProcesso = idProcesso;
    }


    @Override
    public void run() {
        System.out.println("SERVER-> Conexão efetuada com o cliente...");
        try {
            entrada = new ObjectInputStream(socketCliente.getInputStream());
            while (conexao) {
                Mensagem mensagem = (Mensagem) entrada.readObject();
                if (mensagem.getMessage().equalsIgnoreCase("fim"))
                    conexao = false;
                else if(mensagem.getProcessoRemetente() == idProcesso){
                    System.out.println("====-Mensagem enviada foi retornada-====\n" + mensagem.toString());
                } else if(mensagem.getProcessoDestinatario() == idProcesso) {
                    System.out.println("===-Mensagem recebida-===\n" + mensagem.toString());
                } else {

                }
            }
            entrada.close();
            System.out.println("Fim do cliente " + socketCliente.getInetAddress().getHostAddress());
            socketCliente.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}