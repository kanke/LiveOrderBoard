package com.silverbars.lambda;

public class Quantity {

    private Double value;

    Quantity(final double value) {
        this.value = value;
    }

    Double value() {
        return value;
    }

    Quantity sum(final Quantity quantity) {
        return new Quantity(this.value + quantity.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity)) return false;

        Quantity quantity = (Quantity) o;

        if (!value.equals(quantity.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "value=" + value +
                '}';
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(this.value + quantity.value);
    }
}
