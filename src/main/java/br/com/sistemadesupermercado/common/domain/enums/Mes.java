package br.com.sistemadesupermercado.common.domain.enums;

public enum Mes {
    JANEIRO("Janeiro", 1),
    FEVEREIRO("Fevereiro", 2),
    MARCO("Março", 3),
    ABRIL("Abril", 4),
    MAIO("Maio", 5),
    JUNHO("Junho", 6),
    JULHO("Julho", 7),
    AGOSTO("Agosto", 8),
    SETEMBRO("Setembro", 9),
    OUTUBRO("Outubro", 10),
    NOVEMBRO("Novembro", 11),
    DEZEMBRO("Dezembro", 12);

    private String label;
    private Integer mesNumerico;

    private Mes(String label, Integer mesNumerico) {
        this.label = label;
        this.mesNumerico = mesNumerico;
    }

    public String getLabel() {
        return label;
    }

    public Integer getMesNumerico() {
        return mesNumerico;
    }
}
