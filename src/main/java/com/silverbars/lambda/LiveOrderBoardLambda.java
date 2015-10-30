package com.silverbars.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.silverbars.lambda.Quantity.aQuantity;
import static com.silverbars.lambda.Summary.aSummaryOf;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class LiveOrderBoardLambda {

    private static final Comparator<Summary> BY_TYPE = comparing(Summary::type);
    private static final Comparator<Summary> BY_PRICE = (summary, summaryToCompareWith) -> summary.type().comparePrice(summary.price(), summaryToCompareWith.price());

    private List<Order> orders;

    public LiveOrderBoardLambda() {
        orders = new ArrayList<>();
    }

    public List<Summary> summary() {
        Map<Price, Quantity> aggregatedQuantityPerPrice = orders.stream()
                                                            .collect(groupingBy(Order::price,
                                                                    reducing(aQuantity(0.0)
                                                                            , Order::quantityForType
                                                                            , Quantity::sum))
                                                            );

        List<Summary> summaryForPrice =  aggregatedQuantityPerPrice
                                        .entrySet()
                                        .stream()
                                        .map(price -> aSummaryOf(price.getValue(), price.getKey(), Quantity::typeForQuantity))
                                        .collect(toList());

        return summaryForPrice.stream()
                            .sorted(BY_TYPE.thenComparing(BY_PRICE))
                            .collect(toList());
    }


    public void register(String user, Quantity quantity, Price price, OrderType orderType) {
        orders.add(new Order(user, quantity, price, orderType));
    }

    public void cancel(String user, Quantity quantity, Price price, OrderType orderType) {
        orders.remove(new Order(user, quantity, price, orderType));
    }

}
