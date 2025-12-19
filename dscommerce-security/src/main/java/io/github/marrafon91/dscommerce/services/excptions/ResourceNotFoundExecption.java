package io.github.marrafon91.dscommerce.services.excptions;


public class ResourceNotFoundExecption extends  RuntimeException {

    public ResourceNotFoundExecption(String msg) {
        super(msg);
    }
}
