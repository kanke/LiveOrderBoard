package com.silverbars.oo;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public enum OrderType {
    BUY(POSITIVE_INFINITY) {
        public double quantity(double quantity) {
            return Math.abs(quantity);
        }
    },
    SELL(NEGATIVE_INFINITY) {
        public double quantity(double quantity) {
            return Math.abs(quantity) * -1.0;
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

    public static OrderType typeFor(double quantity) {
        return ORDER_TYPE_FOR_SIGN.get(signOf(quantity));
    }

    private static double signOf(double sign) {
        return Math.signum(sign);
    }

    abstract double quantity(double quantity);

}
