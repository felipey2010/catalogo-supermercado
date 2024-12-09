package br.com.sistemadesupermercado.common.business;

import br.com.sistemadesupermercado.common.dao.PaisDAO;
import br.com.sistemadesupermercado.common.domain.model.Pais;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PaisBusiness {

    @Inject
    PaisDAO dao;

    public List<Pais> listarPaises() {
        return dao.listarPaises();
    }

    public Pais carregarPais(Pais pais){
        return dao.carregarPais(pais);
    }
    
    public Pais findById(Integer codPais) {
    	return dao.findById(codPais);
    }
}
