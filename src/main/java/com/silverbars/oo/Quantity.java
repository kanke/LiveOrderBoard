package com.silverbars.oo;

public class Quantity {

    private Double value;

    public Quantity(double value) {
        this.value = value;
    }

    Double value() {
        return value;
    }

    public Quantity sum(Quantity quantity) {
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
}
