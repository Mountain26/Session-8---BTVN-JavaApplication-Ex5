package ra.edu.ex5.custom_validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TourDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTourDates {
    String message() default "Ngày kết thúc phải diễn ra sau ngày khởi hành";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

