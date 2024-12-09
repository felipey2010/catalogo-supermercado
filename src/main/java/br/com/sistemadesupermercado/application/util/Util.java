package br.com.sistemadesupermercado.application.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Util {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static int calcularDigito(String str, int[] peso) {

		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValidCPF(String cpf) {
		if ((cpf == null) || (cpf.length() != 11))
			return false;

		int digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		int digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + Integer.toString(digito1) + Integer.toString(digito2));
	}

	public static boolean isValidCNPJ(String cnpj) {
		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		int digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		int digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + Integer.toString(digito1) + Integer.toString(digito2));
	}

	public static String removeMaskCNPJ(String codigo) {
		if(StringUtils.isEmpty(codigo))
			return "";
		return codigo.replaceAll("[./.-]", "");
	}

	public static String removeMaskCPF(String codigo) {
		if(StringUtils.isEmpty(codigo))
			return "";
		return codigo.replaceAll("[.-]", "");
	}

	public static String removerMaskCEP(String cep) {
		return cep.replace("/[^0-9]+/g", "");
	}

	public static String addMascara(String numeroSemMascara, String mascara) {
		if(StringUtils.isEmpty(numeroSemMascara))
			return "";

		StringBuilder numero = new StringBuilder(numeroSemMascara);
		byte[] bytes = mascara.getBytes();

		int i = 0;
		for (byte b : bytes) {
			if (b != 57) {
				numero.insert(i, "" + (char) b);
			}
			i++;
		}
		return numero.toString();
	}

	public static int getDias(Calendar data) {
		long m2 = Calendar.getInstance().getTimeInMillis();
		long m1 = data.getTimeInMillis();
		return (int) ((m2 - m1) / (24 * 60 * 60 * 1000));
	}

	public static String criptografaToMD5(String dado) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(StandardCharsets.UTF_8.encode(dado));

		return String.format("%032x", new BigInteger(1, md5.digest()));

	}


	public static String leftCompletation(final String entrada, final int length, final char complemento) {

		final StringBuffer out = new StringBuffer(entrada);
		while (out.length() < length) {
			out.insert(0, complemento);
		}
		return out.toString();
	}

	public static String capitalize(String texto) {
		if (texto != null && texto.length() > 2) {
			return Character.toUpperCase(texto.charAt(0)) + texto.substring(1);
		}
		return texto;
	}

	public static String converCoordendaSIRGA(String valor){

        String grau = valor.subSequence(0,2).toString();
        String min = valor.subSequence(2,4).toString();
		int tamanhoSegundo = valor.length();
		String seg = null;
		if(tamanhoSegundo > 5 ){
			 seg = valor.substring(4,6);
		}else{
			seg = valor.substring(4,5);
		}

        String mms = null;
        int tamanho = valor.length();

        if(tamanho > 6){
            mms = valor.substring(6);
            seg = seg + "." + mms;
        }

        StringBuilder result = new StringBuilder();
        result.append(grau).append("°").append(min).append("'").append(seg).append("''");

		return  result.toString();
	}

	public static String imprimirCoordendaSIRGA(String latitude,String longitude){

		String latg = latitude.subSequence(0,2).toString();
		String latm = latitude.subSequence(2,4).toString();
		String lats = latitude.substring(5);

		String longg = longitude.subSequence(0,2).toString();
		String longlm = longitude.subSequence(2,4).toString();
		String longls = longitude.substring(5);

		StringBuilder result = new StringBuilder();
		result.append(latg).append("°").append(latm).append("'").append(lats).append("''");
		return  result.toString();
	}

    public static String imprimirCoordenada(String valor){

        String latg = valor.subSequence(0,2).toString();
        String latm = valor.subSequence(2,4).toString();
        String lats = valor.substring(5);
        StringBuilder result = new StringBuilder();
        result.append(latg).append("º").append(latm).append("'").append(lats).append("''");
        return  result.toString();
    }

	/**
	 * Adiciona dias uteis a uma determinada data
	 * obs: só elimina sábados e domingos
	 * @param date
	 * @param qtdDiasUteis
	 * @return
	 */
	public static Date adicionarDiasUteis(Date date, int qtdDiasUteis) {

		LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

		while(qtdDiasUteis != 0) {
			if(!DayOfWeek.SATURDAY.equals(localDate.getDayOfWeek()) && !DayOfWeek.SUNDAY.equals(localDate.getDayOfWeek())) {
				qtdDiasUteis--;
			}

			localDate = localDate.plusDays(1);
		}

		date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		return date;
	}
}
