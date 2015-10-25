package com.silverbars.lambda;


public enum OrderType {
    BUY {
        @Override
        public int comparePrice(Price priceToCompare, Price priceToCompareWith) {
            return priceToCompareWith.compareTo(priceToCompare);
        }

        @Override
        Quantity quantityForType(Quantity aQuantity) {
            return aQuantity.abs();
        }
    },
    SELL {
        @Override
        public int comparePrice(Price priceToCompare, Price priceToCompareWith) {
            return priceToCompare.compareTo(priceToCompareWith);
        }

        @Override
        Quantity quantityForType(Quantity aQuantity) {
            return aQuantity.negative();
        }
    };

    abstract int comparePrice(Price priceToCompare, Price priceToCompareWith);

    abstract Quantity quantityForType(Quantity aQuantity);

}
