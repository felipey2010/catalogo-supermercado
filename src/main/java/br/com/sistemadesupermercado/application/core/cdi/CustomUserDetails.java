package br.com.sistemadesupermercado.application.core.cdi;

import br.com.sistemadesupermercado.common.domain.model.Perfil;
import br.com.sistemadesupermercado.common.domain.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<Perfil> roles = usuario.getPerfis();
        if (roles == null) {
            return Collections.emptyList();
        }
        return AuthorityUtils.createAuthorityList(roles.stream().map(Perfil::getNome).toArray(String[]::new));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.isAtivo();
    }

    public Usuario getUser() {
        return usuario;
    }
}