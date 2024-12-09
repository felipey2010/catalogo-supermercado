package br.com.sistemadesupermercado.common.business;

import br.com.sistemadesupermercado.common.dao.MensagemGlobalDAO;
import br.com.sistemadesupermercado.common.domain.model.MensagemGlobal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Stateless
public class MensagemGlobalBusiness implements Serializable {

    @Inject
    private MensagemGlobalDAO mensagemGlobalDAO;

    public MensagemGlobal save(MensagemGlobal mensagem) {
        return mensagemGlobalDAO.save(mensagem);
    }

    public MensagemGlobal excluir(MensagemGlobal mensagem) {
        mensagem.setDataRemocao(new Date());
        return this.mensagemGlobalDAO.save(mensagem);
    }

    public MensagemGlobal loadMensagem(MensagemGlobal mensagem){
        return mensagemGlobalDAO.loadMensagem(mensagem);
    }

    public List<MensagemGlobal> listarMensagens(){
        return mensagemGlobalDAO.listarMensagens();
    }

    public List<MensagemGlobal> listarMensagens(boolean global){
        return mensagemGlobalDAO.listarMensagens(global);
    }
}
