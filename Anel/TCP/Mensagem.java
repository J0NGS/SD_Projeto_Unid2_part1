package Anel.TCP;

import java.io.Serializable;

// Classe Mensagem implementa a interface Serializable para permitir a serialização dos objetos
public class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributos da mensagem
    int processoRemetente;
    int processoDestinatario;
    String message;
    TIPO tipo;

    // Construtor para mensagem unicast
    public Mensagem(int processoRemetente, int processoDestinatario, String message, TIPO tipo) {
        this.processoRemetente = processoRemetente;
        this.processoDestinatario = processoDestinatario;
        this.message = message;
        this.tipo = tipo;
    }

    // Construtor para mensagem broadcast
    public Mensagem(int processoRemetente, String message, TIPO tipo) {
        this.processoRemetente = processoRemetente;
        this.processoDestinatario = 0; // Define o processo destinatário como 0 para broadcast
        this.message = message;
        this.tipo = tipo;
    }

    // Getters e Setters para os atributos da mensagem
    public int getProcessoRemetente() {
        return this.processoRemetente;
    }

    public void setProcessoRemetente(int processoRemetente) {
        this.processoRemetente = processoRemetente;
    }

    public int getProcessoDestinatario() {
        return this.processoDestinatario;
    }

    public void setProcessoDestinatario(int processoDestinatario) {
        this.processoDestinatario = processoDestinatario;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TIPO getTipo() {
        return this.tipo;
    }

    public void setTipo(TIPO tipo) {
        this.tipo = tipo;
    }

    // Enumeração para representar o tipo da mensagem
    public enum TIPO {
        BROADCAST, UNICAST
    }

    // Representação textual da mensagem
    @Override
    public String toString() {
        return "{" +
            " processoRemetente= '" + getProcessoRemetente() + "'\n" +
            "processoDestinatario= '" + getProcessoDestinatario() + "'\n" +
            "message= '" + getMessage() + "'\n" +
            "tipo= '" + getTipo() + "'\n" +
            "}";
    }
}


