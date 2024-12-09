package br.com.sistemadesupermercado.application.web.controller.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("myDateTimeConverter")
public class myDateTimeConverter extends DateTimeConverter {

	public myDateTimeConverter() {
        setPattern("HH:mm:ss.SSS");
    }

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && value.length() != getPattern().length()) {
			throw new ConverterException("Invalid format");
		}

		return super.getAsObject(context, component, value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}