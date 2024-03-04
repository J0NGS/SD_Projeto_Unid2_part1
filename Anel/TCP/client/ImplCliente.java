package Anel.TCP.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import Anel.TCP.Mensagem;
import Anel.TCP.Mensagem.TIPO;

// Classe que implementa o comportamento do cliente
public class ImplCliente implements Runnable {
    private Socket cliente; // Socket para comunicação com o servidor
    private boolean conexao = true; // Variável para controlar a conexão
    private ObjectOutputStream saida; // Stream de saída para enviar mensagens ao servidor
    private int idProcesso; // ID do processo do cliente

    // Construtor que recebe o socket do cliente e o ID do processo
    public ImplCliente(Socket c, int idProcesso) {
        this.cliente = c;
        this.idProcesso = idProcesso;
    }

    @Override
    // Método run que define o comportamento do cliente
    public void run() {
        try {
            Scanner teclado = new Scanner(System.in); // Scanner para entrada de dados do usuário
            saida = new ObjectOutputStream(cliente.getOutputStream()); // Inicializa o stream de saída
            System.out.println("LOG_CLIENT-> O cliente conectou ao servidor na porta " + cliente.getPort());

            // Loop principal que permite o cliente enviar mensagens ao servidor
            while (conexao) {
                try {
                    System.out.println("========================================");
                    System.out.println("- Digite 0 para encerrar o cliente\n"
                            + "- Para enviar a mensagem, digite qualquer outro número inteiro\n");

                    int opcao = teclado.nextInt(); // Lê a opção do usuário
                    if (opcao == 0) {
                        conexao = false; // Se a opção for 0, encerra a conexão
                    } else {
                        System.out.println("========================================");
                        System.out.println("Qual o tipo da mensagem?\n" + "1. Broadcast\n" + "2. Unicast\n"
                                + "(Qualquer outro número inteiro será considerado Broadcast)\n");

                        int tipoMensagem = teclado.nextInt(); // Lê o tipo de mensagem do usuário
                        teclado.nextLine(); // Limpa o buffer do teclado

                        if (tipoMensagem == 2) {
                            System.out.println("========================================");
                            System.out.println("Digite o id do processo destinatário");
                            int idDestinatario = teclado.nextInt(); // Lê o ID do destinatário
                            teclado.nextLine(); // Limpa o buffer do teclado

                            System.out.println("========================================");
                            System.out.println("Digite a mensagem que será enviada");
                            String mensagemTexto = teclado.nextLine(); // Lê a mensagem do usuário

                            // Cria uma nova mensagem unicast com os dados fornecidos pelo usuário
                            Mensagem mensagem = new Mensagem(idProcesso, idDestinatario, mensagemTexto, TIPO.UNICAST);
                            saida.writeObject(mensagem); // Envia a mensagem ao servidor
                            saida.flush(); // Limpa o buffer de saída
                            System.out.println("LOG_CLIENT-> Mensagem enviada.");
                        } else {
                            System.out.println("========================================");
                            System.out.println("Digite a mensagem que será enviada");
                            String mensagemTexto = teclado.nextLine(); // Lê a mensagem do usuário

                            // Cria uma nova mensagem broadcast com os dados fornecidos pelo usuário
                            Mensagem mensagem = new Mensagem(idProcesso, mensagemTexto, TIPO.BROADCAST);
                            saida.writeObject(mensagem); // Envia a mensagem ao servidor
                            saida.flush(); // Limpa o buffer de saída
                            System.out.println("LOG_CLIENT-> Mensagem enviada.");
                        }
                    }
                } catch (InputMismatchException e) {
                    // Se o usuário inserir um tipo de dado incorreto, exibe uma mensagem e reinicia o cliente
                    System.out.println("Tipo de dado incorreto. Reiniciando o cliente...");
                    teclado.nextLine(); // Limpa o buffer do teclado
                    conexao = true; // Reinicia a conexão
                }
            }

            saida.close(); // Fecha o stream de saída
            teclado.close(); // Fecha o scanner
            cliente.close(); // Fecha o socket do cliente
            System.out.println("LOG_CLIENT-> Cliente finaliza conexão."); // Mensagem de finalização
        } catch (IOException e) {
            e.printStackTrace(); // Trata exceções de entrada e saída
        }
    }
}

