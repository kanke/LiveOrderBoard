package com.silverbars.lambda;

class Order {

    private final String userId;
    private final Quantity quantity;
    private final Price price;
    private final OrderType orderType;


    Order(final String userId, final Quantity quantity, final Price price, final OrderType orderType) {
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }

    Quantity quantity() {
        return quantity;
    }

    Price price() {
        return price;
    }

    OrderType type() {
        return orderType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (orderType != order.orderType) return false;
        if (!price.equals(order.price)) return false;
        if (!quantity.equals(order.quantity)) return false;
        if (!userId.equals(order.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + orderType.hashCode();
        return result;
    }

}
