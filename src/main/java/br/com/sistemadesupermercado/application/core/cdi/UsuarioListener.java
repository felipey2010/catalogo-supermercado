package br.com.sistemadesupermercado.application.core.cdi;

import br.com.sistemadesupermercado.application.web.SessionMB;
import br.com.sistemadesupermercado.common.domain.security.UsuarioSistema;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioListener implements HttpSessionAttributeListener {

	public static final String SESSION_MB = "sessionMB";
	private static final List<UsuarioSistema> usuarios = new ArrayList<UsuarioSistema>();

	public static int maxQntUsuarioDoMes = 0;
	public static Date horarioMaxQntUsuarioDoMesAtingido = new Date();

	@Override
	public void attributeAdded(HttpSessionBindingEvent evt) {
		if (evt.getName().equals(SESSION_MB)) {
			SessionMB sessaoMB = (SessionMB) evt.getValue();
			usuarios.add(sessaoMB.getUsuario());
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent evt) {
		if (evt.getName().equals(SESSION_MB)){
			SessionMB sessionMB = (SessionMB) evt.getValue();
			usuarios.remove(sessionMB.getUsuario());
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent evt) {}

	public static int getTotalUsuarios() {
		return usuarios.size();
	}

	public static List<UsuarioSistema> getUsuarios() {
		return usuarios;
	}
}