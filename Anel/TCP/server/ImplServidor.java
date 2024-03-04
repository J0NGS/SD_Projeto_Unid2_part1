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

// Classe que implementa o comportamento do servidor para lidar com a comunicação com os clientes
public class ImplServidor implements Runnable {
    private Socket socketCliente;
    private boolean conexao = true;
    private ObjectInputStream entrada;
    private int idProcesso;
    private Cliente clienteProximo;

    // Construtor da classe ImplServidor
    public ImplServidor(Socket cliente, int idProcesso, Cliente clienteProximo) {
        this.socketCliente = cliente;
        this.idProcesso = idProcesso;
        this.clienteProximo = clienteProximo;
    }

    // Método run() é chamado quando a thread é iniciada
    @Override
    public void run() {
        try {
            // Cria um ObjectInputStream para receber dados do cliente
            entrada = new ObjectInputStream(socketCliente.getInputStream());
            // Loop para lidar com a comunicação enquanto a conexão estiver ativa
            while (conexao) {
                try {
                    // Loop para ler mensagens do cliente
                    while (true) {
                        Mensagem mensagem = (Mensagem) entrada.readObject();
                         if (mensagem.getTipo().equals(TIPO.UNICAST)) {
                            // Se a mensagem for do tipo UNICAST
                            if (mensagem.getProcessoRemetente() == idProcesso) {
                                // Se o processo remetente for igual ao ID deste servidor
                                // Exibe a mensagem recebida
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
                                // Se o processo destinatário for igual ao ID deste servidor
                                // Exibe a mensagem recebida e encaminha para o próximo servidor
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
                                // Adiciona uma assinatura à mensagem
                                mensagem.setMessage(mensagem.getMessage()
                                        + " - Mensagem assinada pelo destinatario, processo-> " + idProcesso);
                                // Encaminha a mensagem para o próximo servidor no anel
                                encaminharMensagem(mensagem);
                            } else {
                                // Encaminha a mensagem para o próximo servidor no anel
                                encaminharMensagem(mensagem);
                            }
                        } else if (mensagem.getTipo().equals(TIPO.BROADCAST)) {
                            // Se a mensagem for do tipo BROADCAST
                            if (mensagem.getProcessoRemetente() == idProcesso) {
                                // Se o processo remetente for igual ao ID deste servidor
                                // Exibe a mensagem recebida
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
                                // Se o processo remetente for diferente do ID deste servidor
                                // Exibe a mensagem recebida e encaminha para o próximo servidor
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
                                // Adiciona uma assinatura à mensagem
                                mensagem.setMessage(mensagem.getMessage()
                                        + " - Mensagem assinada pelo destinatario, processo-> " + idProcesso);
                                // Encaminha a mensagem para o próximo servidor no anel
                                encaminharMensagem(mensagem);
                            }
                        }
                    }
                } catch (EOFException e) {
                    // O cliente fechou a conexão, aguardando novas mensagens
                    Thread.sleep(100); // Aguarda 100 ms antes de tentar ler novamente
                }
            }
            // Fecha o ObjectInputStream e o socket
            entrada.close();
            System.out.println("Fim do cliente " + socketCliente.getInetAddress().getHostAddress());
            socketCliente.close();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para encaminhar a mensagem para o próximo servidor no anel
    private void encaminharMensagem(Mensagem mensagem) {
        try {
            // Cria um novo socket para se comunicar com o próximo servidor
            Socket socket = new Socket(clienteProximo.getIp(), clienteProximo.getPorta());
            // Cria uma instância de ImplCliente para enviar a mensagem
            ImplCliente cliente = new ImplCliente(socket, idProcesso);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            // Envia a mensagem através do ObjectOutputStream
            saida.writeObject(mensagem);
            saida.flush();
            System.out.println("LOG_SERVER-> Nova thread do cliente rodando no servidor para encaminhar a mensagem.");
            System.out.println("LOG_SERVER-> Mensagem encaminhada para o próximo processo no anel na porta "
                    + clienteProximo.getPorta());
            // Fecha o ObjectOutputStream e o socket
            saida.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
