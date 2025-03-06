package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

public class AmazonIntegrationTest {
    private Database database;
    private ShoppingCartAdaptor cartAdaptor;
    private List<PriceRule> priceRules;
    private static class DummyPriceRule implements PriceRule {
        @Override
        public double priceToAggregate(List<Item> items) {
            double total = 0;
            for (Item item : items) {
                total += item.getQuantity() * item.getPricePerUnit();
            }
            return total;
        }
    }

    @BeforeEach
    public void setUp() {
        database = new Database();
        database.resetDatabase();
        cartAdaptor = new ShoppingCartAdaptor(database);
        priceRules = new ArrayList<>();
        priceRules.add(new DummyPriceRule());
    }

    @Test
    @DisplayName("specification-based")
    public void testCalculateTotalPrice() {
        Amazon amazon = new Amazon(cartAdaptor, priceRules);
        // Add an item: 2 units at 10.0 each. Expected total = 2 * 10.0 = 20.0.
        Item item = new Item(ItemType.OTHER, "TestItem", 2, 10.0);
        amazon.addToCart(item);

        double expectedTotal = 20.0;
        double actualTotal = amazon.calculate();
        assertEquals(expectedTotal, actualTotal, "The calculated total should match the expected total.");
    }

    @Test
    @DisplayName("structural-based")
    public void testShoppingCartAdaptorIntegration() {
        Amazon amazon = new Amazon(cartAdaptor, priceRules);
        // Add two different items to test the adaptor's integration with the database.
        Item item1 = new Item(ItemType.OTHER, "Item1", 1, 15.0);
        Item item2 = new Item(ItemType.OTHER, "Item2", 3, 5.0);
        amazon.addToCart(item1);
        amazon.addToCart(item2);

        List<Item> items = cartAdaptor.getItems();
        assertEquals(2, items.size(), "ShoppingCartAdaptor should return 2 items.");
    }
}
