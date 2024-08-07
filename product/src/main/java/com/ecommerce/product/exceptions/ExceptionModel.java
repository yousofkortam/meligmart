package com.ecommerce.product.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionModel {

    private int code;
    private String message;
    private String timeStamp;

}
