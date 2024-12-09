package br.com.sistemadesupermercado.application.web;

public enum Mensagem {
    SAVE_PESSOA("Pessoa salva com sucesso."),
    SAVE_SERVIDOR("Servidor salvo com sucesso."),
    SAVE_ALUNO("Aluno salva com sucesso."),

    SAVE_CARGO("Cargo salva com sucesso."),
    SAVE_ORGAO("Órgão salvo com sucesso."),
    SAVE_INSTITUICAO("Instituição salva com sucesso.");

    private String label;

    private Mensagem(String label){
        this.label =label;
    }

    public String getLabel() {
        return label;
    }
}
