package com.gorge4j.user.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;
import com.gorge4j.user.constant.RegexConstant;

/**
 * @Title: Password.java
 * @Description: 用户密码校验器
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-06-24 23:11:51
 * @version v1.0
 */

@Target(value = {ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR,
        ElementType.ANNOTATION_TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Password.Validator.class})
public @interface Password {

    public String message() default "{com.gorge4j.user.validator.password.invalid.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    /** 校验器 Validator 直接写在了注解内部，也可以在外面实现，然后 @Constraint(validatedBy = {Validator.class}) 依赖外部的校验器即可 */
    public static class Validator implements ConstraintValidator<Password, String> {

        @Override
        public void initialize(Password password) {
            // Do nothing
        }

        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (StringUtils.isBlank(value)) {
                return false;
            }
            return Pattern.matches(RegexConstant.REGEX_NAME, value);
        }
    }

}
