package Anel.TCP;

import java.io.Serializable;

public class Mensagem implements Serializable{
    private static final long serialVersionUID = 1L;
    int processoRemetente;
    int processoDestinatario;
    String message;


    public Mensagem(int processoRemetente, int processoDestinatario, String message) {
        this.processoRemetente = processoRemetente;
        this.processoDestinatario = processoDestinatario;
        this.message = message;
    }


    //Getters e Setters
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

    @Override
    public String toString() {
        return "{" +
            " processoRemetente= '" + getProcessoRemetente() + "\n" +
            "processoDestinatario= '" + getProcessoDestinatario() + "\n" +
            "message= '" + getMessage() + "\n" +
            "}";
    }

}

