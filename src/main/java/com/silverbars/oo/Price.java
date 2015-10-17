package com.silverbars.oo;

public class Price {

    private final Integer amount;

    public Price(Integer amount) {
        this.amount = amount;
    }

    public Integer amount() {
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
}
