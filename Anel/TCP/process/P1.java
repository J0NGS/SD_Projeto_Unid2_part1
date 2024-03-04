package Anel.TCP.process;

import Anel.TCP.client.Cliente;
import Anel.TCP.server.Servidor;

public class P1 {
    public static void main(String[] args) {
        Thread server = new Thread(new Servidor(8081, 1));
        Thread client = new Thread(new Cliente("10.0.0.157", 8082, 1));

        server.start();
        client.start();
    }
}

