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
    private Socket socketCliente;
    public static int cont = 0;
    private static int idProcesso = 1;
    private boolean conexao = true;
    private ObjectInputStream entradaObjeto;
    private static Map<Integer, Socket> conexoes = new HashMap<>(); // Mapa de conexões com IDs dos clientes
    private static Map<Integer, ObjectOutputStream> saidas = new HashMap<>(); // Mapa de ObjectOutputStreams para os clientes

    public ImplServidor(Socket cliente) {
        this.socketCliente = cliente;
    }

    @Override
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
            for (Map.Entry<Integer, Socket> entry :  conexoes.entrySet()) {
                if (entry.getValue().equals(socketCliente)) {
                    idProcesso = entry.getKey();
                }
            }
            mensagemRecebida = new Mensagem(0, cont,("Você é o cliente de id-> " + idProcesso),TIPO.UNICAST);
            encaminharMensagem(mensagemRecebida);
            while (conexao) {
                try {
                    mensagemRecebida = (Mensagem) entradaObjeto.readObject();
                    System.out.println("LOG_SERVER-> mensagem recebida");
                    if (mensagemRecebida.getMessage().equalsIgnoreCase("fim"))
                        conexao = false;
                    else if(mensagemRecebida.getTipo().equals(TIPO.UNICAST)){
                        int idRemetente = 0;
                        for (Map.Entry<Integer, Socket> entry :  conexoes.entrySet()) {
                            if (entry.getValue().equals(socketCliente)) {
                                idRemetente = entry.getKey();
                            }
                        }
                        mensagemRecebida.setProcessoRemetente(idRemetente);
                        if (mensagemRecebida.getProcessoDestinatario() == mensagemRecebida.getProcessoRemetente()){
                            System.out.println("LOG_SERVER-> ERRO: DESTINATARIO ENVIANDO MENSAGEM PARA ELE MESMO -> " + mensagemRecebida.getProcessoDestinatario());
                        } else {
                            System.out.println(mensagemRecebida);
                            System.out.println("LOG_SERVER-> ENCAMINHANDO MENSAGEM PARA -> " + mensagemRecebida.getProcessoDestinatario());
                            System.out.println(mensagemRecebida);
                            encaminharMensagem(mensagemRecebida);
                        }
                    }
                    else if(mensagemRecebida.getTipo().equals(TIPO.BROADCAST)){
                        int idRemetente = 0;
                        for (Map.Entry<Integer, Socket> entry :  conexoes.entrySet()) {
                            if (entry.getValue().equals(socketCliente)) {
                                idRemetente = entry.getKey();
                            }
                        }
                        mensagemRecebida.setProcessoRemetente(idRemetente);
                        System.out.println(mensagemRecebida);
                        System.out.println("LOG_SERVER-> ENCAMINHANDO MENSAGEM PARA TODOS OS CLIENTES");
                        for (int indice  : conexoes.keySet()) {
                            if (indice != idRemetente) {
                                mensagemRecebida.setProcessoDestinatario(indice);
                                encaminharMensagem(mensagemRecebida);
                            }
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
        try {
            ObjectOutputStream saidaObjeto = new ObjectOutputStream(conexao.getOutputStream());
            saidas.put(idCliente, saidaObjeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para encaminhar a mensagem para o destinatário correto
    private void encaminharMensagem(Mensagem mensagem) {
        int idDestinatario = mensagem.getProcessoDestinatario();
        ObjectOutputStream saidaObjetoDestino = saidas.get(idDestinatario);
        if (saidaObjetoDestino != null) {
            try {
                saidaObjetoDestino.writeObject(mensagem);
                saidaObjetoDestino.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Destinatário não encontrado para a mensagem ou está offline: " + mensagem.getMessage());
        }
    }
}