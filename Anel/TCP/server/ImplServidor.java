package Anel.TCP.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Anel.TCP.Mensagem;
import Anel.TCP.Mensagem.TIPO;
import Anel.TCP.client.Cliente;
import Anel.TCP.client.ImplCliente;

public class ImplServidor implements Runnable {
    private Socket socketCliente;
    private boolean conexao = true;
    private ObjectInputStream entrada;
    private int idProcesso;
    private Cliente clienteProximo;

    public ImplServidor(Socket cliente, int idProcesso, Cliente clienteProximo) {
        this.socketCliente = cliente;
        this.idProcesso = idProcesso;
        this.clienteProximo = clienteProximo;
    }

    @Override
    public void run() {
        System.out.println("LOG_SERVER-> Conexão efetuada com o cliente...");
        try {
            entrada = new ObjectInputStream(socketCliente.getInputStream());
            while (conexao) {
                try {
                    while (true) {
                        Mensagem mensagem = (Mensagem) entrada.readObject();
                        if (mensagem.getMessage().equalsIgnoreCase("fim")) {
                            conexao = false;
                            break;
                        } else if (mensagem.getTipo().equals(TIPO.UNICAST)) {
                            if (mensagem.getProcessoRemetente() == idProcesso) {
                                System.out.println("\r\n" + //
                                        "___  ___                                                     _                             _       \r\n"
                                        + //
                                        "|  \\/  |                                                    | |                           | |      \r\n"
                                        + //
                                        "| .  . | ___ _ __  ___  __ _  __ _  ___ _ __ ___    _ __ ___| |_ ___  _ __ _ __   __ _  __| | __ _ \r\n"
                                        + //
                                        "| |\\/| |/ _ | '_ \\/ __|/ _` |/ _` |/ _ | '_ ` _ \\  | '__/ _ | __/ _ \\| '__| '_ \\ / _` |/ _` |/ _` |\r\n"
                                        + //
                                        "| |  | |  __| | | \\__ | (_| | (_| |  __| | | | | | | | |  __| || (_) | |  | | | | (_| | (_| | (_| |\r\n"
                                        + //
                                        "\\_|  |_/\\___|_| |_|___/\\__,_|\\__, |\\___|_| |_| |_| |_|  \\___|\\__\\___/|_|  |_| |_|\\__,_|\\__,_|\\__,_|\r\n"
                                        + //
                                        "                              __/ |                                                                \r\n"
                                        + //
                                        "                             |___/                                                                 \r\n"
                                        + //
                                        "" + "\n" + mensagem.toString());
                            } else if (mensagem.getProcessoDestinatario() == idProcesso) {
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
                                encaminharMensagem(mensagem);
                            } else{
                                encaminharMensagem(mensagem);
                            }
                        } else if (mensagem.getTipo().equals(TIPO.BROADCAST)) {
                            if (mensagem.getProcessoRemetente() == idProcesso) {
                                System.out.println("\r\n" + //
                                        "___  ___                                                     _                             _       \r\n"
                                        + //
                                        "|  \\/  |                                                    | |                           | |      \r\n"
                                        + //
                                        "| .  . | ___ _ __  ___  __ _  __ _  ___ _ __ ___    _ __ ___| |_ ___  _ __ _ __   __ _  __| | __ _ \r\n"
                                        + //
                                        "| |\\/| |/ _ | '_ \\/ __|/ _` |/ _` |/ _ | '_ ` _ \\  | '__/ _ | __/ _ \\| '__| '_ \\ / _` |/ _` |/ _` |\r\n"
                                        + //
                                        "| |  | |  __| | | \\__ | (_| | (_| |  __| | | | | | | | |  __| || (_) | |  | | | | (_| | (_| | (_| |\r\n"
                                        + //
                                        "\\_|  |_/\\___|_| |_|___/\\__,_|\\__, |\\___|_| |_| |_| |_|  \\___|\\__\\___/|_|  |_| |_|\\__,_|\\__,_|\\__,_|\r\n"
                                        + //
                                        "                              __/ |                                                                \r\n"
                                        + //
                                        "                             |___/                                                                 \r\n"
                                        + //
                                        "" + "\n" + mensagem.toString());
                            } else {
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
                                // Encaminhar a mensagem para o próximo processo no anel
                                encaminharMensagem(mensagem);
                            }
                        }
                    }
                } catch (EOFException e) {
                    // O cliente fechou a conexão, aguardando novas mensagens
                    Thread.sleep(100); // Aguarda 100 ms antes de tentar ler novamente
                }
            }
            entrada.close();
            System.out.println("Fim do cliente " + socketCliente.getInetAddress().getHostAddress());
            socketCliente.close();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void encaminharMensagem(Mensagem mensagem) {
        // Encaminhar a mensagem para o próximo processo no anel
        try {
            Socket socket = new Socket(clienteProximo.getIp(), clienteProximo.getPorta());
            ImplCliente cliente = new ImplCliente(socket, idProcesso);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            saida.writeObject(mensagem);
            saida.flush();
            System.out.println("LOG_SERVER-> Mensagem encaminhada para o próximo processo no anel.");
            saida.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
