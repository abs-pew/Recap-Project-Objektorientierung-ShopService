import lombok.With;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
@With
public record Order(
        String id,
        List<Product> products,
        OrderStatusList orderStatus,
        ZonedDateTime orderDateTime
)
{
}
