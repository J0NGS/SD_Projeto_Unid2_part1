package Estrela.TCP.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Anel.TCP.Mensagem;
import Anel.TCP.Mensagem.TIPO;

public class ImplServidor implements Runnable {
    public Socket socketCliente;
    public static int cont = 0;
    private boolean conexao = true;
    private ObjectInputStream entradaObjeto;
    private static Map<Integer, Socket> conexoes = new HashMap<>(); // Mapa de conexões com IDs dos clientes


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
                    else if(mensagemRecebida.getTipo().equals(TIPO.UNICAST)){
                        System.out.println("LOG_SERVER-> ENCAMINHANDO MENSAGEM PARA -> " + mensagemRecebida.getProcessoDestinatario());
                        System.out.println(mensagemRecebida);
                    }
                    else if(mensagemRecebida.getTipo().equals(TIPO.BROADCAST)){
                        System.out.println(mensagemRecebida);
                        for (int indice  : conexoes.keySet()) {
                            mensagemRecebida.setProcessoDestinatario(indice);
                            encaminharMensagem(mensagemRecebida);
                        }
                    }
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

    public static synchronized void adicionarConexao(int idCliente, Socket conexao) {
        conexoes.put(idCliente, conexao);
    }

    // Método para encaminhar a mensagem para o destinatário correto
    private void encaminharMensagem(Mensagem mensagem) {
        Socket conexaoDestino = conexoes.get(mensagem.getProcessoDestinatario());
        if (conexaoDestino != null) {
            try {
                ObjectOutputStream saidaObjetoDestino = new ObjectOutputStream(conexaoDestino.getOutputStream());
                saidaObjetoDestino.writeObject(mensagem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Destinatário não encontrado para a mensagem: " + mensagem.getMessage());
        }
    }
}
