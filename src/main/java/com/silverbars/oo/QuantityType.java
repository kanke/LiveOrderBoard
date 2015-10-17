package com.silverbars.oo;

import static com.silverbars.oo.OrderType.typeFor;

public class QuantityType {

    private double quantity;
    private OrderType type;

    private QuantityType(double quantity, OrderType type) {
        this.quantity = type.quantity(quantity);
        this.type = type;
    }

    public static QuantityType aQuantityType(double quantity, OrderType type) {
        return new QuantityType(quantity, type);
    }

    public QuantityType aggregateWith(QuantityType aQuantity) {
        double totalQuantity = this.quantity + aQuantity.quantity;
        return aQuantityType(totalQuantity, typeFor(totalQuantity));
    }

    public double quantity() {
        return Math.abs(quantity);
    }

    public OrderType type() {
        return type;
    }
}
