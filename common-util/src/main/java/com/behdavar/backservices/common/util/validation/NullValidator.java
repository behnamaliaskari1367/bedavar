package com.behdavar.backservices.common.util.validation;

import java.util.Objects;
import java.util.function.Supplier;

public final class NullValidator {

    private NullValidator() {
    }

    /**
     * @param <X>               Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to be thrown
     * @throws X if value is null
     */
    public static <X extends Throwable> void isNullThrow(Object value, Supplier<? extends X> exceptionSupplier) throws X {
        if (Objects.isNull(value)) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void isNullThrow(Supplier<? extends X> exceptionSupplier, Object... values) throws X {
        for (Object value : values) {
            if (Objects.isNull(value)) {
                throw exceptionSupplier.get();
            }
        }
    }
}
