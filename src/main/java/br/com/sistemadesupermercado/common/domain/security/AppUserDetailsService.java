package br.com.sistemadesupermercado.common.domain.security;

import br.com.sistemadesupermercado.application.util.CDIServiceLocator;
import br.com.sistemadesupermercado.common.dao.UsuarioDAO;
import br.com.sistemadesupermercado.common.domain.model.Acesso;
import br.com.sistemadesupermercado.common.domain.model.Usuario;
import br.com.sistemadesupermercado.common.domain.model.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDetailsService implements UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UsuarioDAO usuarioDAO = CDIServiceLocator.getBean(UsuarioDAO.class);
		assert usuarioDAO != null;
		Usuario usuario = usuarioDAO.searchByLogin(login);

		UsuarioSistema user;

		if (usuario != null && usuario.isAtivo()) {
			user = new UsuarioSistema(usuario, getNivelDeAcesso(usuario));
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		return user;
	}

	private Collection<? extends GrantedAuthority> getNivelDeAcesso(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Perfil perfil: usuario.getPerfis()) {
			if(null == perfil.getDataRemocao()) {
				authorities.add(new SimpleGrantedAuthority(perfil.getNome().toUpperCase()));

				for (Acesso acesso : perfil.getAcessos()) {
					SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + acesso.getNome().toUpperCase());

					if(!authorities.contains(role)) {
						authorities.add(role);
					}
				}
			}
		}

		return authorities;
	}

}
