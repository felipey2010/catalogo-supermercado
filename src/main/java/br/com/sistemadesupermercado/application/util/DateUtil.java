package br.com.sistemadesupermercado.application.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    public static final long MILISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
    public static final long MILISECONDS_PER_HOUR = 60 * 60 * 1000;
    public static final long MILISECONDS_PER_MINUTE = 60 * 1000;

    /**
     * RETORNA DATA POR EXTENSO
     */
    public static String getDataPorExtenso(String localidade, Date data) {
        SimpleDateFormat df1 = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",
                new Locale("pt", "BR"));
        try {
            if (localidade == null) {
                return df1.format(data).toLowerCase();
            }
            return localidade + ", " + df1.format(data).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getAnoAtual() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getDataPorExtenso(Date dataEvento) {
        return getDataPorExtenso(null, dataEvento);
    }

    public static String getDateTime(Date data) {
        SimpleDateFormat formatData = new SimpleDateFormat(
                "dd/MM/yyyy 'as' HH:mm:ss");
        return formatData.format(data);
    }

    public static String getDate(Date data) {
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
        return formatData.format(data);
    }

    public static Date getDate(String data) {
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatData.parse(data);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getTime(String time) {
        SimpleDateFormat formatData = new SimpleDateFormat("HH:mm");
        try {
            return formatData.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getIntervalo(long intervalo) {
        String retorno = "";
        if (intervalo >= MILISECONDS_PER_DAY) {
            retorno = intervalo / MILISECONDS_PER_DAY + " dia(s) e ";
            intervalo = intervalo % MILISECONDS_PER_DAY;
        }
        retorno = retorno + intervalo / MILISECONDS_PER_HOUR + " hora(s) e ";
        intervalo = intervalo % MILISECONDS_PER_HOUR;

        return retorno + Math.round(intervalo / MILISECONDS_PER_MINUTE)
                + " minuto(s)";
    }

    public static String getIntervalo(Date dataRecebimento, Date dataEvento) {
        if (dataEvento != null && dataRecebimento != null) {
            long intervalo = dataEvento.getTime() - dataRecebimento.getTime();
            return getIntervalo(intervalo);
        }
        return "";
    }

    public static int getContaDias(Date data) {
        if (data != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            long m2 = Calendar.getInstance().getTimeInMillis();
            long m1 = cal.getTimeInMillis();
            return (int) ((m2 - m1) / (24 * 60 * 60 * 1000));
        } else {
            return 0;
        }
    }

    public static Date getLast(int dia) {
        return getLast(new Date(), dia);
    }

    public static Date getLast(Date date, int dia) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int diaAtual = calendar.get(Calendar.DAY_OF_WEEK);

        int dias = dia - diaAtual;
        if (dias >= 0) {
            dias = dias - 7;
        }
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return calendar.getTime();
    }

    public static Date getPrimeiroDiaMes(Date dataReferencia) {
        Calendar data = Calendar.getInstance();
        data.setTime(dataReferencia);
        data.set(Calendar.DAY_OF_MONTH, 1);
        return data.getTime();
    }

    public static Date getUltimoDiaMes(Date dataReferencia) {
        Calendar data = Calendar.getInstance();
        data.setTime(dataReferencia);
        data.set(Calendar.DAY_OF_MONTH,
                data.getActualMaximum(Calendar.DAY_OF_MONTH));
        return data.getTime();
    }

    public static int getDia(Date data) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(data);
        return instance.get(Calendar.DAY_OF_MONTH);
    }

    public static String getMesAno(Calendar data) {
        return data.getDisplayName(Calendar.MONTH, Calendar.LONG,
                Locale.getDefault()).toUpperCase()
                + "/" + data.get(Calendar.YEAR);
    }

    public static Date getInicioDia(Date data) {
        return DateUtil.getDate(DateUtil.getDate(data));
    }

    public static Date getFimDia(Date data) {
        return new Date(getInicioDia(data).getTime() + MILISECONDS_PER_DAY - 1);
    }

    public static Date getLastDays(Date dataReferencia, int dias) {
        Calendar data = Calendar.getInstance();
        data.setTime(dataReferencia);
        data.add(Calendar.DATE, -dias);
        return data.getTime();
    }

    public static Date add(Date dataReferencia, int dias) {
        Calendar data = Calendar.getInstance();
        data.setTime(dataReferencia);
        data.add(Calendar.DATE, dias);
        return data.getTime();
    }

    public static Date add(Date dataReferencia, int qnt, int unidade) {
        Calendar data = Calendar.getInstance();
        data.setTime(dataReferencia);
        data.add(unidade, qnt);
        return data.getTime();
    }

    public static Date converterDate(Calendar data) {
        Date date = data.getTime();
        return date;
    }

    public static Calendar converterCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date getPrimeiroDiaDoAno() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, 0);
        instance.set(Calendar.DAY_OF_MONTH, instance.getActualMinimum(Calendar.DAY_OF_MONTH));
        return converterDate(instance);
    }

    public static Date getUltimoDiaDoAno() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, 11);
        instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
        return converterDate(instance);
    }

    public static int calculaIdade(Date dataNasc) {

        Calendar dateOfBirth = new GregorianCalendar();

        dateOfBirth.setTime(dataNasc);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        dateOfBirth.add(Calendar.YEAR, age);

        if (today.before(dateOfBirth)) {

            age--;

        }

        return age;

    }
}
