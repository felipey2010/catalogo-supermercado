package br.com.sistemadesupermercado.application.web;

import br.com.sistemadesupermercado.application.core.cdi.Loggado;
import br.com.sistemadesupermercado.common.domain.model.Acesso;
import br.com.sistemadesupermercado.common.domain.model.Perfil;
import br.com.sistemadesupermercado.common.domain.model.Usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Set;


@ManagedBean
@ViewScoped
public class PrincipalController implements Serializable{

    @Inject
    @Loggado
    private Usuario usuario;

    public boolean isAdministrador() {
        boolean ehAdmin = false;
        Set<Perfil> perfis = usuario.getPerfis();
        for(Perfil perfil: perfis) {
            for(Acesso acesso: perfil.getAcessos()){
                if(acesso.getNome().contains("MENU_ADMINISTRACAO")){
                    ehAdmin = true;
                }
            }
        }
        return ehAdmin;
    }

    public boolean isPadrao() {
        boolean ehPadrao = false;
        Set<Perfil> perfis = usuario.getPerfis();
        for(Perfil perfil: perfis) {
            for(Acesso acesso: perfil.getAcessos()){
                if(acesso.getNome().contains("ROLE_PADRAO")){
                    ehPadrao = true;
                }
            }
        }
        return ehPadrao;
    }
}


