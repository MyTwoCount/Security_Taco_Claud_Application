package from.book.Taco_Cloud_Application;

import from.book.Taco_Cloud_Application.data.IngredientRepository;
import from.book.Taco_Cloud_Application.domain.Ingredient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import from.book.Taco_Cloud_Application.domain.Ingredient.Type;

@SpringBootApplication
public class SecurityTacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityTacoCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner prepareData(IngredientRepository repo){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
				repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
				repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
				repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
				repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
				repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
				repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
				repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
				repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
				repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
			}
		};
	}

}
