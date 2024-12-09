package br.com.sistemadesupermercado.common.business;

import br.com.sistemadesupermercado.application.util.Util;
import br.com.sistemadesupermercado.common.dao.PessoaDAO;
import br.com.sistemadesupermercado.common.domain.model.Pessoa;
import br.com.sistemadesupermercado.common.exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.regex.Pattern;

@Stateless
public class PessoaBusiness {

    @Inject
    private PessoaDAO dao;

    public Pessoa save(Pessoa pessoa) throws BusinessException {
        String email = pessoa.getEmail();
        if(!email.isEmpty()) {
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if(!Pattern.compile(regexPattern).matcher(email).matches()) {
                throw new BusinessException("Por favor, informe um email válido!");
            }
        }
        if (!Util.isValidCPF(pessoa.getCodigo())) {
            throw new BusinessException("O CPF informado não é válido.");
        }
        Pessoa pessoaCadastrada = dao.findPessoaByCPF(pessoa.getCodigo());
        if (pessoaCadastrada != null && (!pessoaCadastrada.getId().equals(pessoa.getId()))) {
            throw new BusinessException("Este CPF já está associado à outra pessoa.");
        }

        return dao.save(pessoa);
    }

    /**
     *
     * @param pessoa pessoa física para excluir seus dados
     * @return boolean - true se for executado com exito
     */
    public boolean excluirPessoa(Pessoa pessoa) throws Exception {
        return dao.excluir(pessoa);
    }

    public boolean checarExistenciaCPF(String codigo) {
        Pessoa pessoa = dao.findPessoaByCPF(codigo);
        return pessoa != null;
    }

    public Pessoa findPessoaPorCPF(String codigo) {
        try {
            return dao.findPessoaByCPF(codigo);
        } catch (NoResultException e) {
            return null;
        }
    }
}