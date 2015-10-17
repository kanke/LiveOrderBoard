package com.silverbars.oo;

import java.util.*;

import static com.silverbars.oo.OrderType.BUY;
import static com.silverbars.oo.OrderType.SELL;
import static com.silverbars.oo.QuantityType.aQuantityType;
import static com.silverbars.oo.Summary.aSummaryOf;

public class Summariser {

    private static final QuantityType ZERO_QUANTITY = aQuantityType(0, BUY);

    List<Summary> summarise(final List<Order> orders) {
        final Map<Price, List<QuantityType>> quantitiesByPrice = groupOrderQuantityAndTypeByPrice(orders);

        final List<Summary> summaries = summariseOrdersByAggregating(quantitiesByPrice);
        summaries.sort(byType());
        summaries.sort(byPrice());

        return summaries;
    }

    private Map<Price, List<QuantityType>> groupOrderQuantityAndTypeByPrice(final List<Order> orders) {
        final Map<Price, List<QuantityType>> groupedQuantites = new HashMap<>();

        for (Order order : orders) {
            final Price priceForQuantity = new Price(order.price());
            final QuantityType quantityType = aQuantityType(order.quantity(), order.type());

            if (groupedQuantites.containsKey(priceForQuantity)){
                groupedQuantites.get(priceForQuantity).add(quantityType);
            } else {
                final List<QuantityType> quantityAndTypeForPrice = new ArrayList<>();
                quantityAndTypeForPrice.add(quantityType);
                groupedQuantites.put(priceForQuantity, quantityAndTypeForPrice);
            }
        }

        return groupedQuantites;
    }

    private List<Summary> summariseOrdersByAggregating(final Map<Price, List<QuantityType>> quantityTypePerPrice) {
        final List<Summary> summaries = new ArrayList<>();

        for (Price price : quantityTypePerPrice.keySet()) {
            QuantityType quantityAndType = ZERO_QUANTITY;

            for (QuantityType quantityTypeForPrice : quantityTypePerPrice.get(price)) {
                quantityAndType = quantityAndType.aggregateWith(quantityTypeForPrice);
            }

            summaries.add(aSummaryOf(quantityAndType.quantity(), price.amount(), quantityAndType.type()));
        }

        return summaries;
    }


    private Comparator<Summary> byType() {
        return new Comparator<Summary>() {
            @Override
            public int compare(Summary o1, Summary o2) {
                return o1.type().compareTo(o2.type());
            }
        };
    }

    private Comparator<Summary> byPrice() {
        return new Comparator<Summary>() {
            @Override
            public int compare(Summary o1, Summary o2) {
                int compareResult = 0;
                if (o1.type() == SELL && o2.type() == SELL) {
                    compareResult = o1.price().compareTo(o2.price());
                } else if (o1.type() == BUY && o2.type() == BUY) {
                    compareResult = -1 * o1.price().compareTo(o2.price());
                }

                return compareResult;
            }
        };
    }




}
