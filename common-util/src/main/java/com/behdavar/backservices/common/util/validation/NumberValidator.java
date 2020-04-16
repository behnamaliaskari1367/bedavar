package com.behdavar.backservices.common.util.validation;

import java.util.function.Supplier;

public final class NumberValidator {

    private NumberValidator() {
    }

    public static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static <X extends Throwable> int convertToIntOrThrow(String strNum, Supplier<? extends X> exceptionSupplier) throws X {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            throw exceptionSupplier.get();
        }
    }
}
