package io.github.marrafon91.dscommerce.entities.enums;

public enum OrderStatus {

    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus orderStatus(int code) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (code == orderStatus.getCode()) {
                return orderStatus;
            }
        }
        throw  new IllegalArgumentException("Pedido inválido: " + code);
    }
}
