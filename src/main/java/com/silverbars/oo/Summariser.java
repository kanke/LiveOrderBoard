package com.silverbars.oo;

import java.util.*;

import static com.silverbars.oo.OrderType.BUY;
import static com.silverbars.oo.OrderType.SELL;
import static com.silverbars.oo.OrderType.typeFor;
import static com.silverbars.oo.Summary.aSummaryOf;

public class Summariser {

    private static final Quantity ZERO_QUANTITY = new Quantity(0);

    List<Summary> summarise(final List<Order> orders) {
        final Map<Price, List<Quantity>> quantitiesByPrice = groupOrderQuantityAndTypeByPrice(orders);

        final List<Summary> summaries = summariseOrdersByAggregating(quantitiesByPrice);

        summaries.sort(byType());
        summaries.sort(byPrice());

        return summaries;
    }

    private Map<Price, List<Quantity>> groupOrderQuantityAndTypeByPrice(final List<Order> orders) {
        final Map<Price, List<Quantity>> groupedQuantites = new HashMap<>();

        for (Order order : orders) {
            final Price priceForQuantity = new Price(order.price());
            final Quantity quantity = order.type().quantity(new Quantity(order.quantity()));

            if (groupedQuantites.containsKey(priceForQuantity)){
                groupedQuantites.get(priceForQuantity).add(quantity);
            } else {
                final List<Quantity> quantityForPrice = new ArrayList<>();
                quantityForPrice.add(quantity);
                groupedQuantites.put(priceForQuantity, quantityForPrice);
            }
        }

        return groupedQuantites;
    }

    private List<Summary> summariseOrdersByAggregating(final Map<Price, List<Quantity>> quantityPerPrice) {
        final List<Summary> summaries = new ArrayList<>();

        for (Price price : quantityPerPrice.keySet()) {
            Quantity quantity = ZERO_QUANTITY;

            for (Quantity quantityForPrice : quantityPerPrice.get(price)) {
                quantity = quantity.sum(quantityForPrice);
            }

            summaries.add(aSummaryOf(quantity.abs(), price.amount(), typeFor(quantity.value())));
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
