package br.com.sistemadesupermercado.common.domain.exceptions;

import br.com.sistemadesupermercado.application.exceptions.ApplicationCheckedException;

public class UserException extends ApplicationCheckedException {

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException() {
        super("Usuário já cadastrado.");
    }
}
