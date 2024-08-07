package com.ecommerce.product.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ValidationExceptionModel extends ExceptionModel {

    private final Map<String, String> validations;

    public ValidationExceptionModel(int code, String message, String timeStamp, Map<String, String> validations) {
        super(code, message, timeStamp);
        this.validations = validations;
    }

}
