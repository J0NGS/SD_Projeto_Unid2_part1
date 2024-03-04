package Anel.TCP.process;

import Anel.TCP.client.Cliente;
import Anel.TCP.server.Servidor;

public class P4 {
    public static void main(String[] args) {
        Thread server = new Thread(new Servidor(8084, 4));
        Thread client = new Thread(new Cliente("10.0.0.157", 8081, 4));

        server.start();
        client.start();
    }
}

