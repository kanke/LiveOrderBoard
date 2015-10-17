package com.silverbars.oo;

import static com.silverbars.oo.OrderType.typeFor;

public class QuantityType {

    private Quantity quantity;
    private OrderType type;

    private QuantityType(Quantity quantity, OrderType type) {
        this.quantity = type.quantity(quantity);
        this.type = type;
    }

    public static QuantityType aQuantityType(Quantity quantity, OrderType type) {
        return new QuantityType(quantity, type);
    }

    public QuantityType aggregateWith(QuantityType aQuantity) {
        Quantity totalQuantity = this.quantity.sum(aQuantity.quantity);
        return aQuantityType(totalQuantity, typeFor(totalQuantity.value()));
    }

    public Quantity quantity() {
        return new Quantity(Math.abs(quantity.value()));
    }

    public OrderType type() {
        return type;
    }
}
