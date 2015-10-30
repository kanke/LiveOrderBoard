package com.silverbars.lambda;

public class Price implements Comparable<Price>{

    private final Integer amount;

    Price(final Integer amount) {
        this.amount = amount;
    }

    static Price aPrice(final Integer amount) {
        return new Price(amount);
    }

    @Override
    public int compareTo(final Price priceToCompare) {
        return amount.compareTo(priceToCompare.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;

        if (!amount.equals(price.amount)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                '}';
    }
}
