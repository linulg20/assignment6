package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class AmazonUnitTest {
    private static class StubShoppingCart implements ShoppingCart {
        private final List<Item> items = new ArrayList<>();

        @Override
        public void add(Item item) {
            items.add(item);
        }

        @Override
        public List<Item> getItems() {
            return items;
        }

        @Override
        public int numberOfItems() {
            return items.size();
        }
    }
    private static class StubPriceRule implements PriceRule {
        private final double fixedValue;

        public StubPriceRule(double fixedValue) {
            this.fixedValue = fixedValue;
        }

        @Override
        public double priceToAggregate(List<Item> items) {
            return fixedValue;
        }
    }

    @Test
    @DisplayName("specification-based")
    public void testCalculateWithStubbedComponents() {
        StubShoppingCart stubCart = new StubShoppingCart();
        Item item = new Item(ItemType.OTHER, "UnitTestItem", 1, 100.0);
        stubCart.add(item);
        List<PriceRule> rules = new ArrayList<>();
        rules.add(new StubPriceRule(50.0));

        Amazon amazon = new Amazon(stubCart, rules);
        double result = amazon.calculate();
        assertEquals(50.0, result, "The calculation should return the fixed value from stub price rule.");
    }

    @Test
    @DisplayName("structural-based")
    public void testAddToCartStructure() {
        StubShoppingCart stubCart = new StubShoppingCart();
        Amazon amazon = new Amazon(stubCart, new ArrayList<>());

        Item item = new Item(ItemType.OTHER, "StructuralTestItem", 2, 20.0);
        amazon.addToCart(item);
        assertTrue(stubCart.getItems().contains(item), "The item should be added to the shopping cart.");
    }
}
