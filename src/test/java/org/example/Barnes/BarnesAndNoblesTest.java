package org.example.Barnes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class BarnesAndNobleTest {

    private BookDatabase mockDatabase;
    private BuyBookProcess mockProcess;
    private BarnesAndNoble store;

    @BeforeEach
    void setUp() {
        mockDatabase = mock(BookDatabase.class);
        mockProcess = mock(BuyBookProcess.class);
        store = new BarnesAndNoble(mockDatabase, mockProcess);
    }

    @Test
    @DisplayName("specification-based - Get total price for cart")
    void testGetPriceForCart() {
        Book book1 = new Book("12345", 10, 5);
        Book book2 = new Book("67890", 15, 2);

        when(mockDatabase.findByISBN("12345")).thenReturn(book1);
        when(mockDatabase.findByISBN("67890")).thenReturn(book2);

        Map<String, Integer> order = new HashMap<>();
        order.put("12345", 2);
        order.put("67890", 1);

        PurchaseSummary summary = store.getPriceForCart(order);

        Assertions.assertNotNull(summary);
        Assertions.assertEquals(35, summary.getTotalPrice());
        verify(mockProcess).buyBook(book1, 2);
        verify(mockProcess).buyBook(book2, 1);
    }

    @Test
    @DisplayName("structural-based - Handle unavailable books")
    void testUnavailableBooks() {
        Book book = new Book("12345", 10, 1);

        when(mockDatabase.findByISBN("12345")).thenReturn(book);

        Map<String, Integer> order = new HashMap<>();
        order.put("12345", 5);

        PurchaseSummary summary = store.getPriceForCart(order);

        Assertions.assertNotNull(summary);
        Assertions.assertEquals(10, summary.getTotalPrice());
        Assertions.assertTrue(summary.getUnavailable().containsKey(book));
        Assertions.assertEquals(4, summary.getUnavailable().get(book));
    }
}
