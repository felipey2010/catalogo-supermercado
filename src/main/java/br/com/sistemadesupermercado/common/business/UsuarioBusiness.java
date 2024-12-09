package br.com.sistemadesupermercado.common.business;

import br.com.sistemadesupermercado.application.exceptions.ApplicationException;
import br.com.sistemadesupermercado.application.util.Util;
import br.com.sistemadesupermercado.common.dao.UsuarioDAO;
import br.com.sistemadesupermercado.common.domain.model.Pessoa;
import br.com.sistemadesupermercado.common.domain.model.Usuario;
import br.com.sistemadesupermercado.common.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe que gerencia as regras de negócio para {@link Usuario}
 */
@Stateless
public class UsuarioBusiness implements Serializable {

    public static String[] CARACTERES = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Inject
    private UsuarioDAO dao;

    @Inject
    private PessoaBusiness pessoaBusiness;

    /**
     * Persiste os dados de usuário no banco de dados
     *
     * @param usuario {@link Usuario} a salvar
     * @return Usuario persistido no banco
     */
    public Usuario save(Usuario usuario) throws BusinessException {
        Usuario user;
        try{
            Pessoa pessoa = usuario.getPessoa();
            if(pessoa.getId() == null) {
                usuario.setPessoa(this.pessoaBusiness.save(usuario.getPessoa()));
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }

        try {
            user = dao.save(usuario);
        } catch (Exception e) {
            throw new BusinessException("Usuário não salvo");
        }
        return user;
    }

    /**
     * Método para buscar Usuario pelo login: Por padrão o login é o CPF
     *
     * @param login Login do usuário. Normalmento o CPF
     * @return usuario
     * @throws BusinessException Quando o formato do CPF não for válido
     */
    public Usuario findUsuarioPorLogin(String login) throws BusinessException {
        if (StringUtils.isNotEmpty(login) && (StringUtils.length(login) == 11) && StringUtils.isNumeric(login)) {
            Usuario usr = dao.searchByLogin(login);
            if (usr != null) return usr;
            throw new BusinessException("Usuário não encontrado");
        }
        throw new BusinessException("Formato de login inválido.");
    }

    /**
     * @param pessoa {@link Pessoa} passado como parametro
     * @return {@link Usuario}
     * @throws ApplicationException Retorna um usuário persistido
     */
    public Usuario criarUsuario(Pessoa pessoa) throws ApplicationException, BusinessException {
        Usuario usr = dao.searchByLogin(pessoa.getCodigo());

        if(usr != null){
            throw new BusinessException("Já existe um usuário com este login cadastrado");
        }

        usr = new Usuario(pessoa);
        usr = this.preparaNovoUsuario(usr);
        return dao.save(usr);
    }

    /**
     * @param usuario {@link Usuario}
     * @return {@link Usuario}
     * @throws ApplicationException Quando não for possível Gerar o HASH da senha do usuário
     * @throws BusinessException    Quando não existe perfil padrão salvo no banco
     * @author torres Retorna um objeto usuário com a senha definida e pronta
     * para ser persistida
     */
    public Usuario preparaNovoUsuario(Usuario usuario) throws ApplicationException, BusinessException {
        String senhaTMP = this.gerarSenhaPadrao(usuario.getPessoa());

        usuario.setSenha(criptoGrafaSenha(senhaTMP));
        return usuario;
    }

    /**
     * Gera senha padrão para senha. Formado pelo ano de nascimento concatenado com os ultimos 4 dígitos do CPF
     * EX:
     * Data de nascimento: 05/03/1997
     * CPF: 025.650.452-60
     * <p>
     * Senha: 19975260
     *
     * @param pessoa Pessoa física a coletar a data de nascimento e CPF {@link Pessoa}
     * @return Senha
     */
    private String gerarSenhaPadrao(Pessoa pessoa) {
        String year = String.valueOf(LocalDate.now().getYear());
        String digitosCPF = pessoa.getCodigo().substring(pessoa.getCodigo().length() - 4);
        return year + digitosCPF;
    }

    /**
     * Retorna uma String com uma senha aleatória gerada
     */
    public String gerarRandomSenha() {
        StringBuilder novaSenha = new StringBuilder();
        long random;
        while (novaSenha.length() < 6) {
            random = Math.round(Math.random() * 35);
            novaSenha.append(CARACTERES[(int) random]);
        }
        return novaSenha.toString();
    }

    public Usuario alterarSenha(Usuario usuario, String senhaAtual, String novaSenha, String confirmacaoSenha) throws NoSuchAlgorithmException, BusinessException {
        if (novaSenha == null ||  novaSenha.length() < 5) {
            throw new BusinessException("A senha deve ter no mínimo 5 caractéres.");
        }
        if (!novaSenha.equals(confirmacaoSenha)) {
            throw new BusinessException("As senhas não conferem.");
        }
        String novaSenhaMD5 = Util.criptografaToMD5(novaSenha);
        String senhaAtualMD5 = Util.criptografaToMD5(senhaAtual);
        if (senhaAtualMD5.equals(usuario.getSenha())) {
            usuario.setSenha(novaSenhaMD5);
        } else
            throw new BusinessException("A senha atual está incorreta.");
        return this.save(usuario);
    }

    public Usuario alterarSenha(Pessoa pessoa, String senhaAtual, String novaSenha, String confirmacaoSenha) throws NoSuchAlgorithmException, BusinessException {
        if (novaSenha == null ||  novaSenha.length() < 5) {
            throw new BusinessException("A senha deve ter no mínimo 6 caractéres.");
        }
        if (!novaSenha.equals(confirmacaoSenha)) {
            throw new BusinessException("As senhas não conferem.");
        }

        Usuario usuario = this.findUsuarioPorLogin(pessoa.getCodigo());

        if(usuario != null) {
            String novaSenhaMD5 = Util.criptografaToMD5(novaSenha);
            String senhaAtualMD5 = Util.criptografaToMD5(senhaAtual);
            if (senhaAtualMD5.equals(usuario.getSenha())) {
                usuario.setSenha(novaSenhaMD5);
                return this.save(usuario);
            } else
                throw new BusinessException("A senha atual está incorreta.");
        } else
            throw new BusinessException("Não foi possível encontrar o usuário");
    }

    public Usuario searchByLoginAtivo(String login) throws BusinessException {
        try {
            return dao.searchByLoginAtivo(login);
        } catch (Exception e) {
            throw new BusinessException("Usuário não encontrado");
        }
    }

    private String criptoGrafaSenha(String senha) throws ApplicationException {
        try {
            return Util.criptografaToMD5(senha);
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException("Erro ao gerar senha automática");
        }
    }

    public void redefinirSenha(Usuario usuario) throws BusinessException {
        try {
            String senha = gerarSenhaPadrao(usuario.getPessoa());
            usuario.setSenha(criptoGrafaSenha(senha));
            dao.save(usuario);
        } catch (ApplicationException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Usuario buscarUsuarioPorCPF(String cpf) {
        return dao.findUsuarioPorCPF(cpf);
    }

    public List<Usuario> listarTodosUsuarios(Usuario usuarioLogado) {
        List<Usuario> lista_todos_usuarios = dao.findTodosUsuariosAtivos();
        List<Usuario> lista_filtrado;
        lista_filtrado = lista_todos_usuarios.stream().filter(usuario -> !Objects.equals(usuarioLogado.getLogin(), usuario.getLogin()))
                .collect(Collectors.toList());
        return lista_filtrado;
    }

    public void mudarEstadoUsuario(Usuario usuario) {
        usuario.setAtivo(!usuario.isAtivo());
        this.dao.save(usuario);
    }
}