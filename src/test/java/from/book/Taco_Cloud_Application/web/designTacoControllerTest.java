package from.book.Taco_Cloud_Application.web;

import from.book.Taco_Cloud_Application.SecurityTacoCloudApplication;
import from.book.Taco_Cloud_Application.data.IngredientRepository;
import from.book.Taco_Cloud_Application.data.TacoRepository;
import from.book.Taco_Cloud_Application.domain.Ingredient;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import from.book.Taco_Cloud_Application.domain.Ingredient.Type;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.PrintingResultHandler;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {DesignController.class})
public class designTacoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository tacoRepository;

    private List<Ingredient> ingredientList;

    @BeforeEach
    public void prepareTest(){
        ingredientList = Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Mockito.when(ingredientRepository.findAll()).thenReturn(ingredientList);
    }

    @Test
    public void getDesignTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/design").accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("designTemplate"))
                .andExpect(model().attribute("wrap",ingredientList.subList(0,2)))
                .andExpect(model().attribute("protein",ingredientList.subList(2,4)))
                .andExpect(model().attribute("veggies",ingredientList.subList(4,6)))
                .andExpect(model().attribute("cheese",ingredientList.subList(6,8)))
                .andExpect(model().attribute("sauce",ingredientList.subList(8,10)));

    }
}

