package com.silverbars.oo;

import java.util.ArrayList;
import java.util.List;

import static com.silverbars.oo.Order.anOrderWith;

public class LiveOrderBoard {

    private List<Order> orders;
    private Summariser summariser;

    public LiveOrderBoard() {
        this.orders = new ArrayList<>();
        summariser = new Summariser();
    }

    public void register(final String userId, final double quantity, final int price, final OrderType orderType) {
        orders.add(anOrderWith(userId, quantity, price, orderType));
    }

    public List<Summary> summary() {
        return summariser.summarise(orders);
    }

    public void cancel(final String userId, final double quantity, final int price, final OrderType orderType) {
        orders.remove(anOrderWith(userId, quantity, price, orderType));
    }

}
