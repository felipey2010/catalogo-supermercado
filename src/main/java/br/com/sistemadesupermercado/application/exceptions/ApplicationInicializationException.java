package br.com.sistemadesupermercado.application.exceptions;

public class ApplicationInicializationException extends ApplicationException {

    private static final String DEFAULT_MESSAGE = "Erro nas configurações do sistema: ";

    public ApplicationInicializationException(String message) {
        super(DEFAULT_MESSAGE + message);
    }
}
