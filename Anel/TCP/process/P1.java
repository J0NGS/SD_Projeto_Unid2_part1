package Anel.TCP.process;

import Anel.TCP.client.Cliente;
import Anel.TCP.server.Servidor;

public class P1 {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("10.215.28.218"/*"10.0.0.157" */, 8082, 1);
        Servidor servidor = new Servidor(8081, 1, cliente);
        Thread client = new Thread(cliente);
        Thread server = new Thread(servidor);

        server.start();
        client.start();
    }
}

