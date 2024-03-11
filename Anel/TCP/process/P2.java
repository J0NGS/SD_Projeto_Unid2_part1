package Anel.TCP.process;

import Anel.TCP.client.Cliente;
import Anel.TCP.server.Servidor;

public class P2 {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("10.0.0.157", 8083, 2);
        Servidor servidor = new Servidor(8082, 2, cliente);
        Thread client = new Thread(cliente);
        Thread server = new Thread(servidor);

        server.start();
        client.start();
    }
}

