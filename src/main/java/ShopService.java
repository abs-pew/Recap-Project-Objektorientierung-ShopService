import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    public ProductRepo productRepo;
    public OrderRepo orderRepo;
    public static int orderIdIndex = 0;

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Order addOrder(List<String> productIds) throws ProductOutOfStock {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new ProductOutOfStock("Product with " + productId + " is currently out of stock.");
            }
            products.add(productToOrder.get());
        }


        orderIdIndex = orderIdIndex + 1;
        Order newOrder = new Order("ORD" + orderIdIndex, products, OrderStatusList.PROCESSING);

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

    public void updateOrderStatus(String orderId, OrderStatusList newStatus) throws InvalidOrderId {
        Order orderToUpdate = orderRepo.getOrderById(orderId);
        if (orderToUpdate == null) {
            throw new InvalidOrderId("Order ID " + orderId + " is not a valid order ID.");
        }

        orderToUpdate = orderToUpdate.withOrderStatus(newStatus);
        orderRepo.addOrder(orderToUpdate);
    }
} //Class