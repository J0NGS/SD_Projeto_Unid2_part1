package Anel.TCP.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import Anel.TCP.Mensagem;

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
            System.out.println("CLIENT-> O cliente conectou ao servidor");
            Scanner teclado = new Scanner(System.in);
            saida = new ObjectOutputStream(cliente.getOutputStream());

            while (conexao) {
                System.out.println("1. Para enviar uma mensagem\n" + "0. Encerrar cliente\n");
                int opcao = teclado.nextInt();
                if (opcao == 0)
                    conexao = false;
                else {
                    System.out.println("Digite o id do processo destinatario");
                    int idDestinatario = teclado.nextInt();
                    teclado.nextLine();
                    System.out.println("Digite a mensagem que vai ser enviada");
                    String mensagemTexto = teclado.nextLine();
                    Mensagem mensagem = new Mensagem(idProcesso, idDestinatario, mensagemTexto);
                    saida.writeObject(mensagem);
                    saida.flush(); // Certifique-se de que todos os bytes sejam escritos
                    System.out.println("Mensagem enviada.");
                }
            }
            saida.close();
            teclado.close();
            cliente.close();
            System.out.println("Cliente finaliza conexão.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}