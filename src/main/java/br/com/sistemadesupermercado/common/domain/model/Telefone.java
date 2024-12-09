package br.com.sistemadesupermercado.common.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Telefone implements Serializable {

	public static final String PATTERN = "(\\d{2})(\\d{4}|\\d{5})(\\d{4})$";
	
	@Column(name = "numero", nullable = false)
	private String numero;

	public Telefone() {
		super();
	}

	public void removerCaracteresTelefone() {
		this.numero = numero.replaceAll("\\D", "");
	}

	public String getNumeroFormatado() {
		if (numero != null) {
			return numero.replaceAll(PATTERN, "($1)$2-$3");
		} else {
			return "";
		}
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;

		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

}
