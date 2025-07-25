import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        stockProducts(productRepo);

        OrderRepo orderRepo = new OrderMapRepo();

        ShopService macShopService = new ShopService(productRepo, orderRepo);

try {
    macShopService.addOrder(List.of("MAC002", "MAC004"));
    // System.out.println(macShopService.orderRepo.toString());

    macShopService.addOrder(List.of("MAC001"));
    macShopService.addOrder(List.of("MAC006"));

} catch (ProductOutOfStock e) {
    System.out.println(e.getMessage());
}

      macShopService.orderRepo.getOrders().forEach(System.out::println);

      //  macShopService.getOrdersByStatus("Processing");
        try {
            macShopService.updateOrderStatus("ORD2", OrderStatusList.IN_DELIVERY);
        } catch (InvalidOrderId e) {
            System.out.println(e.getMessage());
        }

      //  macShopService.getOrdersByStatus("In_devlivery");
        macShopService.orderRepo.getOrders().forEach(System.out::println);
    }

    public static void stockProducts(ProductRepo productRepo) {
        productRepo.addProduct(new Product("MAC001", "M1"));
        productRepo.addProduct(new Product("MAC002", "M2"));
        productRepo.addProduct(new Product("MAC003", "M3"));
        productRepo.addProduct(new Product("MAC004", "Air"));

        productRepo.products.forEach(System.out::println);
    }
}
