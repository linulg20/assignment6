package org.example.Barnes; // ✅ Match the correct package of the main classes

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BarnesAndNobleTest {

    @Test
    @DisplayName("specification-based - Should create a book successfully")
    void testBookCreation() {
        Book book = new Book("123456789", 39, 5); // ✅ Corrected constructor arguments
        assertEquals(39, book.getPrice());
        assertEquals(5, book.getQuantity());
    }

    @Test
    @DisplayName("structural-based - Should retrieve book price correctly")
    void testRetrieveBookPrice() {
        Book book = new Book("987654321", 50, 10);
        assertEquals(50, book.getPrice()); // ✅ Ensures the price retrieval works
    }
}
