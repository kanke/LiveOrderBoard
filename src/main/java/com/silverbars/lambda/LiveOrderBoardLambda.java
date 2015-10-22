package com.silverbars.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class LiveOrderBoardLambda {

    private static final Comparator<Summary> BY_TYPE = comparing(Summary::type);
    private static final Comparator<Summary> BY_PRICE = (summary, summaryToCompareWith) -> summary.type().comparePrice(summary.price(), summaryToCompareWith.price());

    private List<Order> orders;

    public LiveOrderBoardLambda() {
        orders = new ArrayList<>();
    }

    public List<Summary> summary() {
        Map<Integer, List<Summary>> summariesByPrice = orders.stream()
                                                    .map(order -> new Summary(new Quantity(order.quantity()), order.price(), order.type()))
                                                    .collect(groupingBy(Summary::price));

        List<Summary> summaryForPrice = summariesByPrice
                                                .values()
                                                .stream()
                                                .map(summaryList -> summaryList.stream()
                                                        .reduce((sum1, sum2) -> sum1.add(sum2))
                                                        .get())
                                                .collect(toList());

        return summaryForPrice.stream()
                            .sorted(BY_TYPE)
                            .sorted(BY_PRICE)
                            .collect(toList());
    }


    public void register(String user, double quantity, int price, OrderType orderType) {
        orders.add(new Order(user, quantity, price, orderType));
    }

    public void cancel(String user, double quantity, int price, OrderType orderType) {
        orders.remove(new Order(user, quantity, price, orderType));
    }


}
