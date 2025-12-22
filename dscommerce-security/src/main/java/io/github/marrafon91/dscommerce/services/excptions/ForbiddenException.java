package io.github.marrafon91.dscommerce.services.excptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
