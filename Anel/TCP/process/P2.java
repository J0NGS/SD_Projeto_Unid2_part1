package Anel.TCP.process;

import Anel.TCP.client.Cliente;
import Anel.TCP.server.Servidor;

public class P2 {
    public static void main(String[] args) {
        Thread server = new Thread(new Servidor(8082, 2));
        Thread client = new Thread(new Cliente("10.0.0.157", 8083, 2));

        server.start();
        client.start();
    }
}

