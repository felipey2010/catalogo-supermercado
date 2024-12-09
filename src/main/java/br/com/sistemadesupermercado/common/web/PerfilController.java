package br.com.sistemadesupermercado.common.web;

import br.com.sistemadesupermercado.common.business.AcessoBusiness;
import br.com.sistemadesupermercado.common.business.PerfilBusiness;
import br.com.sistemadesupermercado.common.domain.model.Acesso;
import br.com.sistemadesupermercado.common.domain.model.Perfil;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class PerfilController implements Serializable {

    @Inject
    private PerfilBusiness perfilBusiness;

    @Inject
    private AcessoBusiness acessoBusiness;

    private Perfil perfil;

    private List<Perfil> listaDePerfis;

    private List<Perfil> listaFiltrada;

    private List<Acesso> listaDeAcessos;

    private boolean desabilitado;

    private Perfil perfilAtivarDesativar;

    @PostConstruct
    protected void init() {
        this.perfil = null;
        this.getPerfisAtivos();
    }

    public void novoPerfil() {
        this.perfil = new Perfil();
        this.perfil.setEditable(true);
        this.listaDePerfis = null;
        this.desabilitado = false;
        this.getAllAcessos();
    }

    public void salvar() {
        try {
            this.perfilBusiness.save(this.perfil);
            this.limparTela();
            Messages.create("Perfil salvo com sucesso.").add();
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void salvarContinuar() {
        try {
            this.perfilBusiness.save(this.perfil);
            this.perfil = new Perfil();
            this.getAllAcessos();
            Messages.create("Perfil salvo com sucesso.").add();
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    public void limparTela() {
        this.perfil = null;
        this.desabilitado = false;
        this.getPerfisAtivos();
    }

    public void detalhar() {
        this.desabilitado = true;
        this.getAllAcessos();
    }

    public void ativarDesativarPerfil() {
        try {
            this.perfilBusiness.togglePerfilAtivo(this.perfilAtivarDesativar);
        } catch (Exception e) {
            Messages.create(e.getMessage()).error().add();
        }
    }

    public void editar() {
        this.getAllAcessos();
    }

    private void getPerfisAtivos() {
        try {
            this.listaDePerfis = this.perfilBusiness.listarPerfisAtivos();
        } catch (Exception e) {
            Messages.create(e.getMessage()).warn().add();
        }
    }

    private void getAllAcessos() {
        try {
            this.listaDeAcessos = this.acessoBusiness.listarAcessos();
        } catch (Exception e) {
            Messages.create(e.getMessage()).error().add();
        }
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Perfil> getListaDePerfis() {
        return listaDePerfis;
    }

    public List<Acesso> getListaDeAcessos() {
        return listaDeAcessos;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public List<Perfil> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Perfil> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public Perfil getPerfilAtivarDesativar() {
        return perfilAtivarDesativar;
    }

    public void setPerfilAtivarDesativar(Perfil perfilAtivarDesativar) {
        this.perfilAtivarDesativar = perfilAtivarDesativar;
    }

}
