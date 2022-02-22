package com.classpath.ordermicroservice.contraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MailIdValidator implements ConstraintValidator<MailId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.contains("gmail.com") ? true: false;

    }
}