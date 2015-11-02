package com.silverbars.lambda;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public enum OrderType {
    BUY(quantity -> quantity.is(value -> value >= 0)) {
        @Override
        public int comparePrice(Price priceToCompare, Price priceToCompareWith) {
            return HIGH_PRICE.compare(priceToCompare, priceToCompareWith);
        }

        @Override
        Quantity quantityForType(Quantity aQuantity) {
            return aQuantity.abs();
        }
    },
    SELL(quantity -> quantity.is(value -> value < 0)) {
        @Override
        public int comparePrice(Price priceToCompare, Price priceToCompareWith) {
            return LOW_PRICE.compare(priceToCompare, priceToCompareWith);
        }

        @Override
        Quantity quantityForType(Quantity aQuantity) {
            return aQuantity.negative();
        }
    };

    private final Predicate<Quantity> quantityForType;
    private final OrderType type;

    private static final Comparator<Price> LOW_PRICE = (p1, p2) -> p1.compareTo(p2);
    private static final Comparator<Price> HIGH_PRICE = LOW_PRICE.reversed();
    private static final Map<Predicate<Quantity>, OrderType> quantityToTypeMatcher = new HashMap<>();

    OrderType(Predicate<Quantity> testForType) {
        quantityForType = testForType;
        type = this;
    }

    static {
        Arrays.stream(OrderType.values()).forEach(orderType -> quantityToTypeMatcher.put(orderType.quantityForType, orderType.type));
    }

    static OrderType typeForQuantity(Quantity quantity) {
        return quantityToTypeMatcher.entrySet().stream()
                .filter(currentMatcher -> currentMatcher.getKey().test(quantity))
                .map(y -> y.getValue())
                .findFirst()
                .get();
    }


    abstract int comparePrice(Price priceToCompare, Price priceToCompareWith);

    abstract Quantity quantityForType(Quantity aQuantity);

}
