package br.com.sistemadesupermercado.application.core.cdi;

import br.com.sistemadesupermercado.common.domain.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ejb.EJB;
import java.io.Serializable;

@Service
public class UsuarioSecurity implements UserDetailsService, Serializable {

    private static final long serialVersionUID = -811449048449639502L;

    @EJB(mappedName = "java:global/sistemadesupermercado/AuthenticationSecurity")
    private AuthenticationSecurity authenticationSecurity;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = authenticationSecurity.searchByLogin(login);
        if (usuario == null) {
            throw new UsernameNotFoundException("Nenhum usuário encontrado");
        }
        if(!usuario.isAtivo()){
            throw new UsernameNotFoundException("Usuário não está ativo");
        }
        return new CustomUserDetails(usuario);
    }
}