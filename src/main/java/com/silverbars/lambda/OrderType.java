package com.silverbars.lambda;


public enum OrderType {
    BUY {
        @Override
        public int comparePrice(Price priceToCompare, Price priceToCompareWith) {
            return priceToCompareWith.compareTo(priceToCompare);
        }
    },
    SELL {
        @Override
        public int comparePrice(Price priceToCompare, Price priceToCompareWith) {
            return priceToCompare.compareTo(priceToCompareWith);
        }
    };

    abstract int comparePrice(Price priceToCompare, Price priceToCompareWith);

}
