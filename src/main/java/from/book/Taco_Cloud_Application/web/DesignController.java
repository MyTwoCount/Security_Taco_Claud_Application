package from.book.Taco_Cloud_Application.web;

import from.book.Taco_Cloud_Application.data.IngredientRepository;
import from.book.Taco_Cloud_Application.domain.Ingredient;
import from.book.Taco_Cloud_Application.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/design")
public class DesignController {

    private List<Ingredient> ingredients;

    private IngredientRepository ingredientRepo;

    @Autowired
    public DesignController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void prepareModel(Model model){
        this.ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach((ingredient -> {
            ingredients.add(ingredient);
        }));

        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type: types){
            model.addAttribute(type.name().toLowerCase(),filterByType(type,ingredients));
        }

        model.addAttribute("design",new Taco());

    }

    @GetMapping
    public String getDesignTemplate(){
        return "designTemplate";
    }

    private List<Ingredient> filterByType(Ingredient.Type type, List<Ingredient> ingredients) {
        return ingredients.stream().filter((i)->{
            return i.getType().name().equals(type.name());
        }).collect(Collectors.toList());
    }

}
