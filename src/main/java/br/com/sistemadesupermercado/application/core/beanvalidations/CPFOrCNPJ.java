package br.com.sistemadesupermercado.application.core.beanvalidations;

import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;

@CPF
@CNPJ
@ReportAsSingleViolation
@ConstraintComposition(OR)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface CPFOrCNPJ {

    String message() default "{CPFOrCNPJ.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}