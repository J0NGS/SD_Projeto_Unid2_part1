package Anel.TCP.process;

import Anel.TCP.client.Cliente;
import Anel.TCP.server.Servidor;

public class P3 {
    public static void main(String[] args) {
        Thread server = new Thread(new Servidor(8083, 3));
        Thread client = new Thread(new Cliente("10.0.0.157", 8084, 3));

        server.start();
        client.start();
    }
}

