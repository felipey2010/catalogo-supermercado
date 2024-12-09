package br.com.sistemadesupermercado.common.domain.enums;

import java.util.Comparator;

public enum DiaDaSemana implements Comparator<DiaDaSemana> {
    DOMINGO("Domingo"),
    SEGUNDA_FEIRA("Segunda-feira"),
    TERCA_FEIRA("Terça-feira"),
    QUARTA_FEIRA("Quarta-feira"),
    QUINTA_FEIRA("Quinta-feira"),
    SEXTA_FEIRA("Sexta-feira"),
    SABADO("Sábado");

    private String dia;

    DiaDaSemana(String dia) {
        this.dia = dia;
    }

    public String getDia() {
        return dia;
    }

    @Override
    public int compare(DiaDaSemana o1, DiaDaSemana o2) {
        if(o1.ordinal() < o2.ordinal()) return 1;
        if(o1.ordinal() > o2.ordinal()) return -1;
        return 0;
    }
}
