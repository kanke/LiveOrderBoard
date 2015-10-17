package com.silverbars.oo;

import static com.silverbars.oo.OrderType.typeFor;

public class QuantityType {

    private Quantity quantity;
    private OrderType type;

    private QuantityType(final Quantity quantity, final OrderType type) {
        this.quantity = type.quantity(quantity);
        this.type = type;
    }

    static QuantityType aQuantityType(final Quantity quantity, final OrderType type) {
        return new QuantityType(quantity, type);
    }

    QuantityType aggregateWith(final QuantityType aQuantity) {
        Quantity totalQuantity = this.quantity.sum(aQuantity.quantity);
        return aQuantityType(totalQuantity, typeFor(totalQuantity.value()));
    }

    Quantity quantity() {
        return new Quantity(Math.abs(quantity.value()));
    }

    OrderType type() {
        return type;
    }
}
