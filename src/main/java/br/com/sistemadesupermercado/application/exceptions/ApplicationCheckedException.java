package br.com.sistemadesupermercado.application.exceptions;

/**
 * Classe geral da aplicação para exceções não checadas.
 * Observar que, caso lançada no contexto de uma transação,
 * sinalizamos para que não seja feito o Rollback automático da transação
 */
@javax.ejb.ApplicationException(rollback = false)
public class ApplicationCheckedException extends Exception {

    public ApplicationCheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationCheckedException(String message) {
        super(message);
    }

    public ApplicationCheckedException(Throwable cause) {
        super(cause);
    }

    public ApplicationCheckedException() {
        super("Ocorreu um erro na aplicação. Entre em contato com o Administrador do Sistema");
    }
}
