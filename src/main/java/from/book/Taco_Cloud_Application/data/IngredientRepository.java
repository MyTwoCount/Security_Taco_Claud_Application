package from.book.Taco_Cloud_Application.data;

import from.book.Taco_Cloud_Application.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,String> {
}
