package from.book.Taco_Cloud_Application.web;

import from.book.Taco_Cloud_Application.data.IngredientRepository;
import from.book.Taco_Cloud_Application.data.TacoRepository;
import from.book.Taco_Cloud_Application.domain.Ingredient;
import from.book.Taco_Cloud_Application.domain.OrderTaco;
import from.book.Taco_Cloud_Application.domain.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/design")
@Slf4j
@SessionAttributes(names = {"orderTaco"})
public class DesignController {

    private List<Ingredient> ingredients;

    private IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;

    @Autowired
    public DesignController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
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
        model.addAttribute("orderTaco",new OrderTaco());

        log.info("create taco object");
    }

    @GetMapping
    public String getDesignTemplate(){
        return "designTemplate";
    }

    @PostMapping
    public String postDesignTemplate(@Valid @ModelAttribute(name = "design") Taco design, Errors errors, OrderTaco orderTaco){

        if(errors.hasErrors()){
            return "designTemplate";
        }

        Taco savedTaco = tacoRepo.save(design);
        orderTaco.addTacoToOrder(savedTaco);

        log.info("Save object design using jpa method: "+design);
        return "redirect:/current/orders";
    }

    private List<Ingredient> filterByType(Ingredient.Type type, List<Ingredient> ingredients) {
        return ingredients.stream().filter((i)->{
            return i.getType().name().equals(type.name());
        }).collect(Collectors.toList());
    }

}
