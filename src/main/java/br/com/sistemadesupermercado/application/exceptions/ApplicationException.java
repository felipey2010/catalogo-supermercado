package br.com.sistemadesupermercado.application.exceptions;

/**
 * Classe geral da aplicação para exceções não checadas.
 * Observar que, caso lançada no contexto de uma transação, forçamos o Rollback da transação
 */
@javax.ejb.ApplicationException(rollback = true)
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException() {
        super("Ocorreu um erro irrecuperável na aplicação. Entre em contato com o Administrador do Sistema");
    }
}
