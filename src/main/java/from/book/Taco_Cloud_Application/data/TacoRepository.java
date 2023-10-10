package from.book.Taco_Cloud_Application.data;

import from.book.Taco_Cloud_Application.domain.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco,Long> {
}
