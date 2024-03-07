package Estrela.TCP.client;

import java.io.IOException;
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
                System.out.println("Digite uma mensagem: ");
                String mensagem = teclado.nextLine();
                if (mensagem.equalsIgnoreCase("fim"))
                    conexao = false;
                else{
                    Mensagem mensagemObjeto = new Mensagem(1, 1, mensagem, TIPO.UNICAST);
                    saida.writeObject(mensagemObjeto);
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
