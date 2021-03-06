package com.silverbars.oo;

import java.util.HashMap;
import java.util.Map;

import static com.silverbars.oo.Quantity.aQuantity;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public enum OrderType {
    BUY(POSITIVE_INFINITY) {
        public Quantity quantity(final Quantity quantity) {
            return new Quantity(Math.abs(quantity.value()));
        }
    },
    SELL(NEGATIVE_INFINITY) {
        public Quantity quantity(final Quantity quantity) {
            return aQuantity(Math.abs(quantity.value())).negative();
        }
    };

    private static Map<Double, OrderType> ORDER_TYPE_FOR_SIGN = new HashMap<>();
    private final double sign;

    OrderType(double sign) {
        this.sign = Math.signum(sign);
    }

    static {
        for (OrderType orderType : OrderType.values()) {
            ORDER_TYPE_FOR_SIGN.put(orderType.sign, orderType);
        }
    }

    public static OrderType typeFor(final double quantity) {
        return ORDER_TYPE_FOR_SIGN.get(signOf(quantity));
    }

    private static double signOf(final double sign) {
        return Math.signum(sign);
    }

    abstract Quantity quantity(Quantity quantity);

}
