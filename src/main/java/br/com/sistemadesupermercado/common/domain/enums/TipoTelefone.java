package br.com.sistemadesupermercado.common.domain.enums;
/**
 * Enum de Tipo Telefone que permite escolher entre Telefone Fixo, Celular ou Comercial no sistema
 */
public enum TipoTelefone {
    COnt("Fixo"), CELULAR("Celular"), COMERCIAL("Comercial");

    private String label;

    private TipoTelefone(String nome) {
        this.label = nome;
    }

	public String getLabel() {
		return label;
	}
}
