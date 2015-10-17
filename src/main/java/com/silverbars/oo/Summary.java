package com.silverbars.oo;

public class Summary {

    private final double quantity;
    private final Integer price;
    private final OrderType orderType;

    Summary(final double quantity, final int price, final OrderType orderType) {
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }

    static Summary aSummaryOf(final double quantity, final int price, final OrderType orderType) {
        return new Summary(quantity, price, orderType);
    }

    double quantity() {
        return Math.abs(quantity);
    }

    Integer price() {
        return price;
    }

    OrderType type() {
        return orderType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Summary)) return false;

        Summary summary = (Summary) o;

        if (Double.compare(summary.quantity, quantity) != 0) return false;
        if (orderType != summary.orderType) return false;
        if (!price.equals(summary.price)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(quantity);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + price.hashCode();
        result = 31 * result + orderType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", orderType=" + orderType +
                '}';
    }
}
