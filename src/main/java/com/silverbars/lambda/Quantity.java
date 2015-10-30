package com.silverbars.lambda;

public class Quantity {

    private Double value;

    Quantity(final double value) {
        this.value = value;
    }

    static Quantity aQuantity(final double value) {
        return new Quantity(value);
    }

    static Quantity sum(final Quantity q1, final Quantity q2) {
        return new Quantity(q1.value + q2.value);
    }

    Quantity abs() {
        return new Quantity(Math.abs(value));
    }

    Quantity negative() {
        return aQuantity(Math.abs(value) * -1.0);
    }

    boolean greaterThanEqualToZero() {
        return value >= 0.0;
    }

    boolean lessThanZero() {
        return value < 0.0;
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
