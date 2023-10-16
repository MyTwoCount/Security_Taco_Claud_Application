package from.book.Taco_Cloud_Application.data;

import from.book.Taco_Cloud_Application.domain.OrderTaco;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderTaco,Long> {
}
