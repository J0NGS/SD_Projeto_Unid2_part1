package Estrela.TCP.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
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
            iniciarThreadRecepcao(); // recebendo mensagens
            while (conexao) {
                System.out.println("===============================");
                System.out.println("Deseja enviar uma mensagem ou encerrar a aplicação?");
                System.out.println("0. Fechar");
                System.out.println("Qualquer outro inteiro envia uma mensagem para um processo destinatario...");
                System.out.println("===============================");
                try {
                    if (teclado.nextInt() == 0) {
                        teclado.nextLine();
                        conexao = false;
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, insira um número válido.");
                    teclado.nextLine(); // Limpar o buffer do scanner
                    continue; // Reinicia o loop
                }
                System.out.println("===============================");
                System.out.println("Qual o tipo da mensagem?");
                System.out.println("0. Broadcast");
                System.out.println("Qualquer outro inteiro. Unicast");
                System.out.println("===============================");
                try {
                    if (teclado.nextInt() == 0) {
                        teclado.nextLine();
                        System.out.println("Digite uma mensagem: ");
                        String mensagem = teclado.nextLine();
                        Mensagem mensagemObjeto = new Mensagem(0, 0, mensagem, TIPO.BROADCAST);
                        saida.writeObject(mensagemObjeto);
                        System.out.println("LOG_CLIENT-> Mensagem enviada");
                    } else {
                        teclado.nextLine();
                        System.out.println("Digite o id do destinatario: ");
                        int destinatario = teclado.nextInt();
                        teclado.nextLine();
                        System.out.println("Digite uma mensagem: ");
                        String mensagem = teclado.nextLine();
                        Mensagem mensagemObjeto = new Mensagem(0, destinatario, mensagem, TIPO.UNICAST);
                        saida.writeObject(mensagemObjeto);
                        System.out.println("LOG_CLIENT-> Mensagem enviada");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, insira um número válido.");
                    teclado.nextLine(); // Limpar o buffer do scanner
                }
            }
            pararRecebimentoMensagens();
            System.out.println("LOG_CLIENT-> parando de receber mensagens");
            saida.close();
            teclado.close();
            cliente.close();
            System.out.println("Cliente finaliza conexão.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Thread de recepção de mensagens
    public void iniciarThreadRecepcao() {
        recebendoMensagens = true;
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
                if (entradaObjeto != null) {
                    try {
                        entradaObjeto.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void pararRecebimentoMensagens() {
        recebendoMensagens = false;
    }
}
