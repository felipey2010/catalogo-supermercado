package br.com.sistemadesupermercado.common.web;

import br.com.sistemadesupermercado.application.util.DateUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class LoginController implements Serializable {
	
	private static final long serialVersionUID = 2968729844333431037L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	private String usuario;

	private boolean showMessage;

	private int year;

	@PostConstruct
	public void preRender() {
		this.showMessage = "true".equals(request.getParameter("invalid"));
		this.year = DateUtil.getAnoAtual();
	}

	public void efetuarLogin() throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
		dispatcher.forward(request, response);

		facesContext.responseComplete();
	}

	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean isShowMessage() {
		return showMessage;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
