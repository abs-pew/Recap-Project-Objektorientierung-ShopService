import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        Main.stockProducts(productRepo);
        OrderRepo orderRepo = new OrderMapRepo();

        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");

        //WHEN
          try {
              Order actual = shopService.addOrder(productsIds);
        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apple")), OrderStatusList.PROCESSING, ZonedDateTime.now());

        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
          } catch (ProductOutOfStock e) {
              System.out.println(e.getMessage());
          }
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        Main.stockProducts(productRepo);
        OrderRepo orderRepo = new OrderMapRepo();

        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1", "2");

        //WHEN
        try {
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
        } catch (ProductOutOfStock e) {
        System.out.println(e.getMessage());
    }
    }
}
