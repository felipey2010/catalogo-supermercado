package br.com.sistemadesupermercado.application.util;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class FacesUtil {

	public static Object getBean(String nome) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		return FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, nome);
	}

	public static void setBean(String mb, Object o) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(mb, o);
	}

	public static void cleanSubmittedValues(UIComponent component) {
		if (component != null) {
			if (component instanceof EditableValueHolder) {
				EditableValueHolder evh = (EditableValueHolder) component;
				evh.setSubmittedValue(null);
				evh.setValue(null);
				evh.setLocalValueSet(false);
				evh.setValid(true);
			}
			if (component.getChildCount() > 0) {
				for (UIComponent child : component.getChildren()) {
					cleanSubmittedValues(child);
				}
			}
		}
	}

	public static void addMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message));
	}

	public static void addMessage(String message, Severity severity) {
		FacesContext instance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = instance.getExternalContext();
		externalContext.getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(severity, message, null));
	}

	public static String getContextPath() {
		return "/" + FacesContext.getCurrentInstance().getExternalContext().getContextName();
	}
	
	public static String getRealPath() {
		return getRealPath("/");
	}
	
	public static String getRealPath(String path) {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
	}

	public static void redirect(String pagina){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(getContextPath() + pagina);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void setMB(String mbName, Object mb) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(mbName, mb);
	}
	
	public static Object getMB(String mbName) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(mbName);
	}
}
