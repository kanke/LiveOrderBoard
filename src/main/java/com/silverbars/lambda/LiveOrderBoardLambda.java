package com.silverbars.lambda;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class LiveOrderBoardLambda {

    List<Order> orders;

    public LiveOrderBoardLambda() {
        orders = new ArrayList<>();
    }

    public List<Summary> summary() {
        return orders.stream()
                .map(order -> new Summary(new Quantity(order.quantity()), order.price(), order.type()))
                .sorted(comparing(Summary::type))
                .sorted((sum1, sum2) -> {
                    if (sum1.type() == OrderType.SELL) {
                        return byPrice(sum1, sum2);
                    } else {
                        return byPrice(sum2, sum1);
                    }
                })
                .collect(toList());
    }

    private int byPrice(Summary sum1, Summary sum2) {
        return sum1.price().compareTo(sum2.price());
    }

    public void register(String user, double quantity, int price, OrderType orderType) {
        orders.add(new Order(user, quantity, price, orderType));
    }

    public void cancel(String user, double quantity, int price, OrderType orderType) {
        orders.remove(new Order(user, quantity, price, orderType));
    }


}
