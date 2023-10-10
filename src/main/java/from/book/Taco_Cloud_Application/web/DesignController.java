package from.book.Taco_Cloud_Application.web;

import from.book.Taco_Cloud_Application.data.IngredientRepository;
import from.book.Taco_Cloud_Application.domain.Ingredient;
import from.book.Taco_Cloud_Application.domain.Taco;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/design")
@Slf4j
public class DesignController {

    private List<Ingredient> ingredients;

    private IngredientRepository ingredientRepo;

    @Autowired
    public DesignController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void prepareModel(Model model) {
        this.ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach((ingredient -> {
            ingredients.add(ingredient);
        }));

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.name().toLowerCase(), filterByType(type, ingredients));
        }

        model.addAttribute("design", new Taco());
            log.info("create taco object")
        ;
    }

    @GetMapping
    public String getDesignTemplate(){
        return "designTemplate";
    }

    @PostMapping
    public String postDesignTemplate(Taco design){
        log.info("Save object design using jpa method: "+design);
        return "redirect:/";
    }

    private List<Ingredient> filterByType(Ingredient.Type type, List<Ingredient> ingredients) {
        return ingredients.stream().filter((i)->{
            return i.getType().name().equals(type.name());
        }).collect(Collectors.toList());
    }

}
