package br.com.sistemadesupermercado.common.exceptions;

import br.com.sistemadesupermercado.application.exceptions.ApplicationCheckedException;

/**
 * Classe geral da aplicação para exceções não checadas.
 * Observar que, caso lançada no contexto de uma transação, foríamos o Rollback da transação
 */
@javax.ejb.ApplicationException(rollback = true)
public class BusinessException extends ApplicationCheckedException {

    private static final long serialVersionUID = 1l;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException() {
        super("Ocorreu um erro durante a execução da transação");
    }
}
