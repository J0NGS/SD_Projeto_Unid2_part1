package Anel.TCP.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import Anel.TCP.Mensagem;
import Anel.TCP.Mensagem.TIPO;

public class ImplCliente implements Runnable {
    private Socket cliente;
    private boolean conexao = true;
    private ObjectOutputStream saida;
    private int idProcesso;

    public ImplCliente(Socket c, int idProcesso) {
        this.cliente = c;
        this.idProcesso = idProcesso;
    }

    @Override
    public void run() {
        try {
            System.out.println("LOG_CLIENT-> O cliente conectou ao servidor");
            Scanner teclado = new Scanner(System.in);
            saida = new ObjectOutputStream(cliente.getOutputStream());

            while (conexao) {
                System.out.println("========================================");
                System.out.println("- Digete 0 para encerrar o cliente\n"
                        + "- Para enviar a mensagem digite qualquer outro inteiro\n");
                int opcao = teclado.nextInt();
                if (opcao == 0)
                    conexao = false;
                else {
                    System.out.println("========================================");
                    System.out.println("Qual o tipo da mensagem?\n" + "1. Broadcast\n" + "2. Unicast\n"
                            + "(Qualquer outro inteiro será considerado Broadcast)\n");
                    int tipoMensagem = teclado.nextInt();
                    teclado.nextLine();
                    if (tipoMensagem == 2) {
                        System.out.println("========================================");
                        System.out.println("Digite o id do processo destinatario");
                        int idDestinatario = teclado.nextInt();
                        teclado.nextLine();
                        System.out.println("========================================");
                        System.out.println("Digite a mensagem que vai ser enviada");
                        String mensagemTexto = teclado.nextLine();
                        Mensagem mensagem = new Mensagem(idProcesso, idDestinatario, mensagemTexto, TIPO.UNICAST);
                        saida.writeObject(mensagem);
                        saida.flush(); // Certifique-se de que todos os bytes sejam escritos
                        System.out.println("LOG_CLIENT-> Mensagem enviada.");
                    } else {
                        System.out.println("========================================");
                        System.out.println("Digite a mensagem que vai ser enviada");
                        String mensagemTexto = teclado.nextLine();
                        Mensagem mensagem = new Mensagem(idProcesso, mensagemTexto, TIPO.BROADCAST);
                        saida.writeObject(mensagem);
                        saida.flush();
                        System.out.println("LOG_CLIENT-> Mensagem enviada.");
                    }

                }
            }
            saida.close();
            teclado.close();
            cliente.close();
            System.out.println("LOG_CLIENT-> Cliente finaliza conexão.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}