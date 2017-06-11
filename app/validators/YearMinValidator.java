package validators;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YearMinValidator implements ConstraintValidator<IYearValidator, Integer> {

	private static final int minValue = 30;

	public static final String message = "O carro deve ter menos de 30 anos.";
	private final String messageYearEmpty = "Informe o ano.";

	public void initialize(IYearValidator constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer year, ConstraintValidatorContext context) {
		if (year == null || year == 0) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(this.messageYearEmpty).addConstraintViolation();
			return false;
		}
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		return (currentYear - year) < minValue;
	}
}