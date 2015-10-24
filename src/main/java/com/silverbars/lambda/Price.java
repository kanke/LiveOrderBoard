package com.silverbars.lambda;

public class Price implements Comparable<Price>{

    private final Integer amount;

    Price(final Integer amount) {
        this.amount = amount;
    }

    static Price aPrice(Integer amount) {
        return new Price(amount);
    }

    Integer amount() {
        return amount;
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
    public int compareTo(Price priceToCompare) {
        return amount.compareTo(priceToCompare.amount);
    }

}
