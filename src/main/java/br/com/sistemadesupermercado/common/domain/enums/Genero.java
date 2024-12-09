package br.com.sistemadesupermercado.common.domain.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum de Genero para permitir a criacao de titulos/nomes masculinos e femininos no sistema
 */
public enum Genero {
    MASCULINO("Masculino", 1, "M"),
    FEMININO("Feminino", 2, "F");
//    DEFINIR("Especificar:");

    private String label;
    private int id;
    private String abbrev;

    Genero(String label, int id, String abbrev){
        this.label = label;
        this.id = id;
        this.abbrev = abbrev;
    }

    public String getLabel() { return label; }

    public int getId() {
        return id;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public static List<Genero> showList() {
        return Arrays.asList(Genero.values());
    }
}
