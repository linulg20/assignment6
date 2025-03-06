
We start this test class by adding the necessary JUnit and Mockito 
libraries. Then we create mock objects for the BookDatabase and
BuyBookProcess, as well as a test instance of the BarnesAndNoble 
shop. We set up these mocks in the @BeforeEach-annotated setUp() 
method to make sure that each test has a clean environment. 
We then use these mocks to create the BarnesAndNoble instance.
In the first test method, testGetPriceForCart, we describe two
books with specific ISBNs and prices. We tell the mock database to 
return these books when it is queried, and we make an order map that
asks for two copies of one book and one copy of the other. Following 
the call to getPriceForCart, the test makes sure that the purchase
summary is not empty, that the total price is figured correctly (35),
and that the buyBook method was called with the right parameters for 
each book. In the second test, testUnavailableBooks, the mock database
sends back a book that is out of stock and places an order for more 
copies than are available to simulate a situation where the quantity 
purchased is greater than the quantity that is available. Then, it 
makes sure that the buy summary only shows the stock that is available
for pricing and correctly lists the extra copies that aren't available.
Overall, these tests make sure that the BarnesAndNoble class works
properly with its outside dependencies and that price estimates and 
inventory management work as they should.

During the integration tests, I wanted to
make sure that all of the system's parts work well together.
Using the provided Database and ShoppingCartAdaptor classes, 
I set up a real database connection. To keep the environment clean,
I restart the database before each test. To make the fake pricing rule
(DummyPriceRule) work like the real one, the total price for each thing
was found by adding up the quantities and the prices per unit. 
This let me test how the Amazon class works as a whole, making
sure that when items are added to the shopping cart, the final
price is calculated properly and matches the expected value, and
that the shopping cart adaptor gets items from the database
correctly.

My main goal in the unit tests was to separate the Amazon class
so that I could test its own functions without any outside dependencies
getting in the way. To do this, I made stub versions of the
ShoppingCart and PriceRule interfaces. The StubShoppingCart kept
a list of things in memory, and the StubPriceRule always gave back
a fixed value. With these stubs in place, we tested the calculate() 
method to make sure it correctly added up the pricing rules and the
addToCart() method to make sure that things were added correctly.
Using @DisplayName to label the tests helped tell the difference 
between specification-based tests (which make sure the class meets 
its requirements) and structural-based tests (which check how the 
class interacts and behaves internally).
