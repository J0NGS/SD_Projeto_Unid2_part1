package Estrela.TCP.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import Anel.TCP.Mensagem;
import Anel.TCP.Mensagem.TIPO;

public class ImplCliente implements Runnable {
    private Socket cliente;
    private boolean conexao = true;
    private ObjectOutputStream saida;
    private ObjectInputStream entradaObjeto;
    private volatile boolean recebendoMensagens = true;

    public ImplCliente(Socket c) {
        this.cliente = c;
    }

    @Override
    public void run() {
        try {
            System.out.println("O cliente conectou ao servidor");
            // Prepara para leitura do teclado
            // Cria objeto para enviar a mensagem ao servidor
            saida = new ObjectOutputStream(cliente.getOutputStream());
            // Envia mensagem ao servidor
            Scanner teclado = new Scanner(System.in);
            while (conexao) {
                iniciarThreadRecepcao(); // recebendo mensagens
                System.out.println("Digite uma mensagem: ");
                String mensagem = teclado.nextLine();
                if (mensagem.equalsIgnoreCase("fim"))
                    conexao = false;
                else {
                    Mensagem mensagemObjeto = new Mensagem(1, 2, mensagem, TIPO.UNICAST);
                    saida.writeObject(mensagemObjeto);
                }
                pararRecebimentoMensagens();
            }
            saida.close();
            teclado.close();
            cliente.close();
            System.out.println("Cliente finaliza conexão.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarThreadRecepcao() {
        recebendoMensagens = true;
        // Thread para receber mensagens do servidor
        new Thread(() -> {
            try {
                entradaObjeto = new ObjectInputStream(cliente.getInputStream());
                while (recebendoMensagens) {
                    Mensagem mensagem = (Mensagem) entradaObjeto.readObject();
                    System.out.println("\r\n" + //
                    "___  ___                                                              _     _     _       \r\n"
                    + //
                    "|  \\/  |                                                             | |   (_)   | |      \r\n"
                    + //
                    "| .  . | ___ _ __  ___  __ _  __ _  ___ _ __ ___    _ __ ___  ___ ___| |__  _  __| | __ _ \r\n"
                    + //
                    "| |\\/| |/ _ | '_ \\/ __|/ _` |/ _` |/ _ | '_ ` _ \\  | '__/ _ \\/ __/ _ | '_ \\| |/ _` |/ _` |\r\n"
                    + //
                    "| |  | |  __| | | \\__ | (_| | (_| |  __| | | | | | | | |  __| (_|  __| |_) | | (_| | (_| |\r\n"
                    + //
                    "\\_|  |_/\\___|_| |_|___/\\__,_|\\__, |\\___|_| |_| |_| |_|  \\___|\\___\\___|_.__/|_|\\__,_|\\__,_|\r\n"
                    + //
                    "                              __/ |                                                       \r\n"
                    + //
                    "                             |___/                                                        \r\n"
                    + //
                    "" + "\n" + mensagem.toString());
                }
            } catch (IOException | ClassNotFoundException e) {
                if (recebendoMensagens) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    entradaObjeto.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void pararRecebimentoMensagens() {
        recebendoMensagens = false;
    }
}
