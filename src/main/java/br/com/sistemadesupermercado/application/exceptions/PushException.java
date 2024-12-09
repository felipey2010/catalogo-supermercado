package br.com.sistemadesupermercado.application.exceptions;

@javax.ejb.ApplicationException(rollback = false)
public class PushException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PushException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushException(String message) {
        super(message);
    }

    public PushException(Throwable cause) {
        super(cause);
    }
}
