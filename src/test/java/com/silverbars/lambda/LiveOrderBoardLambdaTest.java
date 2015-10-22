package com.silverbars.lambda;

import org.hamcrest.Matcher;
import org.hamcrest.number.OrderingComparison;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.silverbars.lambda.OrderType.BUY;
import static com.silverbars.lambda.OrderType.SELL;
import static com.silverbars.lambda.Summary.aSummaryOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class LiveOrderBoardLambdaTest {


    private static final double A_QUANTITY = 3.5;
    private static final int A_PRICE = 303;
    private static final int LOW_PRICE = 100;
    private static final int HIGH_PRICE = 200;
    private static final int LOW_BUY_PRICE = 100;
    private static final int HIGH_BUY_PRICE = 200;
    private static final int HIGH_SELL_PRICE = 400;
    private static final int LOW_SELL_PRICE = 300;
    private static final String ANY_USER = "user1";
    private static final double ANY_QUANTITY = 5.0;
    private LiveOrderBoardLambda orderBoard;
    private List<Summary> orderSummary;

    @Before
    public void createSUT() {
        orderBoard = new LiveOrderBoardLambda();
    }


    @Test public void
    boardEmpty_WhenNoOrders() {
        assertThat(orderBoard.summary(), is(empty()));
    }


    @Test public void
    boardContainsOneOrder_WhenSingleBuyOrderRegistered() {
        orderBoard.register("user1", A_QUANTITY, A_PRICE, BUY);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(1));
        assertOrderSummaryContains(A_QUANTITY, A_PRICE, BUY);
    }


    @Test public void
    boardContainsOneOrder_WhenSingleSellOrderRegistered() {
        orderBoard.register("user1", A_QUANTITY, A_PRICE, SELL);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(1));
        assertOrderSummaryContains(A_QUANTITY, A_PRICE, SELL);
    }


    @Test public void
    boardContainsTwoOrders_WhenTwoDifferentOrdersAreRegistered() {
        orderBoard.register("user1", 3.5, 303, BUY);
        orderBoard.register("user1", 5.0, 503, SELL);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(2));
        assertOrderSummaryContains(3.5, 303, BUY);
        assertOrderSummaryContains(5.0, 503, SELL);
    }


    @Test public void
    boardContainsNoOrders_WhenOrderIsRegisteredAndThenCancelled() {
        orderBoard.register("user1", 3.5, 303, BUY);
        orderBoard.cancel("user1", 3.5, 303, BUY);

        assertThat(orderBoard.summary(), is(empty()));
    }



    @Test public void
    boardContainsOneOrder_WhenTwoDifferentOrdersRegisteredAndThenOneCancelled() {
        orderBoard.register("user1", 3.5, 303, BUY);
        orderBoard.register("user2", 3.5, 403, BUY);

        orderBoard.cancel("user1", 3.5, 303, BUY);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(1));
        assertOrderSummaryContains(3.5, 403, BUY);
    }

    @Test public void
    boardContainsNoOrder_WhenTwoDifferentOrdersAreRegisteredAndThenBothCancelled() {
        orderBoard.register("user1", 3.5, 303, BUY);
        orderBoard.register("user2", 3.5, 403, SELL);

        orderBoard.cancel("user1", 3.5, 303, BUY);
        orderBoard.cancel("user2", 3.5, 403, SELL);

        assertThat(orderBoard.summary().size(), is(0));
    }


    @Test public void
    boardShowsLowestPriceOrderFirst_WhenTwoSellOrdersAreRegistered() {
        orderBoard.register("user1", 5.0, HIGH_PRICE, SELL);
        orderBoard.register("user2", 5.0, LOW_PRICE, SELL);

        orderSummary = orderBoard.summary();

        assertOrderSummary(0, 5.0, LOW_PRICE);
        assertOrderSummary(1, 5.0, HIGH_PRICE);
    }

    @Test public void
    boardShowsHighestPriceOrderFirst_WhenTwoBuyOrdersAreRegistered() {
        orderBoard.register("user1", 5.0, LOW_PRICE, BUY);
        orderBoard.register("user2", 5.0, HIGH_PRICE, BUY);

        orderSummary = orderBoard.summary();

        assertOrderSummary(0, 5.0, HIGH_PRICE);
        assertOrderSummary(1, 5.0, LOW_PRICE);
    }


    @Test public void
    boardShowsOrdersInSequence_WhenBuyAndSellOrdersWithDifferentPriceAreRegistered() {
        registerOrderWith(LOW_BUY_PRICE, BUY);
        registerOrderWith(HIGH_BUY_PRICE, BUY);
        registerOrderWith(HIGH_SELL_PRICE, SELL);
        registerOrderWith(LOW_SELL_PRICE, SELL);

        orderSummary = orderBoard.summary();

        assertThat(orderBoard.summary().size(), is(4));
        assertThat(positionInBoardOfOrderWith(HIGH_BUY_PRICE, BUY), isHigherThan(positionInBoardOfOrderWith(LOW_BUY_PRICE, BUY)));
        assertThat(positionInBoardOfOrderWith(LOW_SELL_PRICE, SELL), isHigherThan(positionInBoardOfOrderWith(HIGH_SELL_PRICE, SELL)));
    }


    @Test public void
    boardShowsOrdersInSequence_WhenBuyAndSellOrdersXWithDifferentPriceAreRegistered() {
        registerOrderWith(LOW_BUY_PRICE, BUY);
        registerOrderWith(HIGH_SELL_PRICE, SELL);
        registerOrderWith(HIGH_BUY_PRICE, BUY);
        registerOrderWith(LOW_SELL_PRICE, SELL);

        orderSummary = orderBoard.summary();

        assertThat(orderBoard.summary().size(), is(4));
        assertThat(positionInBoardOfOrderWith(HIGH_BUY_PRICE, BUY), isHigherThan(positionInBoardOfOrderWith(LOW_BUY_PRICE, BUY)));
        assertThat(positionInBoardOfOrderWith(LOW_SELL_PRICE, SELL), isHigherThan(positionInBoardOfOrderWith(HIGH_SELL_PRICE, SELL)));
    }

    @Test public void
    boardShowsAggregatedQuantity_WhenTwoBuyOrdersWithSamePriceAreRegistered() {
        orderBoard.register("user1", 5.0, LOW_PRICE, BUY);
        orderBoard.register("user2", 3.5, LOW_PRICE, BUY);

        orderSummary = orderBoard.summary();

        assertThat(orderBoard.summary().size(), is(1));
        assertOrderSummaryContains(8.5, LOW_PRICE, BUY);
    }

    @Test public void
    boardShowsAggregatedQuantity_WhenMultipleBuyOrdersWithSomeAtSamePriceAreRegistered() {
        orderBoard.register("user1", 5.0, LOW_PRICE, BUY);
        orderBoard.register("user2", 3.5, LOW_PRICE, BUY);
        orderBoard.register("user3", 10.0, HIGH_PRICE, BUY);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(2));
        assertOrderSummaryContains(10.0, HIGH_PRICE, BUY);
        assertOrderSummaryContains(8.5, LOW_PRICE, BUY);
    }

    @Test public void
    boardShowsAggregatedQuantity_WhenMultipleSellOrdersWithSomeAtSamePriceAreRegistered() {
        orderBoard.register("user1", 4.0, HIGH_PRICE, SELL);
        orderBoard.register("user2", 3.5, HIGH_PRICE, SELL);
        orderBoard.register("user3", 7.0, LOW_PRICE, SELL);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(2));
        assertOrderSummaryContains(7.0, LOW_PRICE, SELL);
        assertOrderSummaryContains(7.5, HIGH_PRICE, SELL);
    }

    @Test public void
    boardShowsAggregatedQuantity_WhenMultipleDifferentTypesOfOrderAreRegistered() {
        orderBoard.register("user1", 4.0, HIGH_PRICE, SELL);
        orderBoard.register("user2", 3.5, HIGH_PRICE, SELL);
        orderBoard.register("user3", 5.0, LOW_PRICE, BUY);
        orderBoard.register("user4", 3.5, LOW_PRICE, BUY);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(2));
        assertOrderSummaryContains(7.5, HIGH_PRICE, SELL);
        assertOrderSummaryContains(8.5, LOW_PRICE, BUY);
    }

    @Ignore
    public void
    boardShowsAggregatedQuantity_WhenMultipleDifferentTypesOfOrderForTheSamePriceAreRegistered() {
        orderBoard.register("user1", 4.0, HIGH_PRICE, SELL);
        orderBoard.register("user4", 3.5, HIGH_PRICE, BUY);

        orderSummary = orderBoard.summary();

        assertThat(orderSummary.size(), is(1));
        assertOrderSummaryContains(0.5, HIGH_PRICE, SELL);
    }

    private void registerOrderWith(int price, OrderType type) {
        orderBoard.register(ANY_USER, ANY_QUANTITY, price, type);
    }

    private int positionInBoardOfOrderWith(final int price, final OrderType type) {
        return orderSummary.indexOf(aSummaryOf(new Quantity(ANY_QUANTITY), price, type));
    }

    private void assertOrderSummaryContains(final double quantity, final int price, final OrderType type) {
        assertThat(orderSummary, hasItem(new Summary(new Quantity(quantity), price, type)));
    }

    private void assertOrderSummary(final int position, final double quantity, final int price) {
        assertThat(orderSummary.get(position).quantity(), is(quantity));
        assertThat(orderSummary.get(position).price(), is(price));
    }

    public static <T extends Comparable<T>> Matcher<T> isHigherThan(T value) {
        return OrderingComparison.lessThan(value);
    }

}
