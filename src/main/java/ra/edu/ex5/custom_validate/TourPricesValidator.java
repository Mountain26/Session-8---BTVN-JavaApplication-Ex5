package ra.edu.ex5.custom_validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ra.edu.ex5.model.dto.TourDto;

public class TourPricesValidator implements ConstraintValidator<ValidTourPrices, TourDto> {
    @Override
    public boolean isValid(TourDto tour, ConstraintValidatorContext context) {
        if (tour == null || tour.getAdultPrice() == null || tour.getChildPrice() == null) {
            return true; // Bỏ qua nếu null (để @NotNull tự xử lý)
        }
        boolean isValid = tour.getChildPrice() <= tour.getAdultPrice();

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("childPrice").addConstraintViolation();
        }

        return isValid;
    }
}

