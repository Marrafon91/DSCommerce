package io.github.marrafon91.dscommerce.services.excptions;


public class DatabaseExecption extends RuntimeException {

    public DatabaseExecption(String msg) {
        super(msg);
    }
}
