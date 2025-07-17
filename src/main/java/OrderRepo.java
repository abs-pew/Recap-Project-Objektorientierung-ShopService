import java.util.List;

public interface OrderRepo {

    List<Order> getOrders();

    Order getOrderById(String id) throws InvalidOrderId;

    Order addOrder(Order newOrder);

    void removeOrder(String id);
}
