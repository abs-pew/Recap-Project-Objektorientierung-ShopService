import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    public ProductRepo productRepo;
    public OrderRepo orderRepo;

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatusList.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> allOrders = orderRepo.getOrders();
        List<Order> ordersByStatus = new ArrayList<>();
        ordersByStatus = allOrders.stream()
                .filter(found -> found.toString().toUpperCase().contains(status.toUpperCase()))
                .collect(Collectors.toList());

        System.out.println(ordersByStatus.stream().count() + " orders found with status " + status.toUpperCase());

        return ordersByStatus;
    }
} //Class