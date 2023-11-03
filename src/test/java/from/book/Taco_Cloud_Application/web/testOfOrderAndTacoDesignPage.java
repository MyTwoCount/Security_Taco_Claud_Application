package from.book.Taco_Cloud_Application.web;

import from.book.Taco_Cloud_Application.data.IngredientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.Duration;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class testOfOrderAndTacoDesignPage {

    @Autowired
    private IngredientRepository ingredientRepo;
    @LocalServerPort
    private int port;

    private static HtmlUnitDriver browser;

    @BeforeAll
    public static void prepareBrowser(){
        browser = new HtmlUnitDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    public static void quitBrowser(){
        browser.quit();
    }

    @Test
    public void testDesignATacoPage_HappyPath() throws Exception {
        browser.get(getHomeUrl());

        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        clickBuildAnotherTaco();
        buildAndSubmitATaco("Another Taco", "COTO", "CARN", "JACK", "LETC", "SRCR");
        fillInAndSubmitOrderForm();
        Assertions.assertEquals(getHomeUrl(), browser.getCurrentUrl());
    }

    @Test
    public void testDesignATacoPage_EmptyOrderInfo() throws Exception {
        browser.get(getHomeUrl());
        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        Assertions.assertEquals(getHomeUrl(), browser.getCurrentUrl());
    }

    @Test
    public void testDesignATacoPage_InvalidOrderInfo() throws Exception {
        browser.get(getHomeUrl());
        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        submitInvalidOrderForm();
        fillInAndSubmitOrderForm();
        Assertions.assertEquals(getHomeUrl(), browser.getCurrentUrl());
    }

    private void submitInvalidOrderForm() {
        browser.findElement(By.id("deliveryName")).sendKeys("Piotr");
        browser.findElement(By.id("deliveryStreet")).sendKeys("Pazia");
        browser.findElement(By.id("deliveryCity")).sendKeys("Wawa");
        browser.findElement(By.id("deliveryState")).sendKeys("pl");
        browser.findElement(By.id("deliveryZip")).sendKeys("129");
        browser.findElement(By.id("ccNumber")).sendKeys("abc123");
        browser.findElement(By.id("ccExpiration")).sendKeys("12-12");
        browser.findElement(By.id("ccCVV")).sendKeys("bh45");

        browser.findElement(By.id("orderSubmitButton")).submit();
    }

    private void submitEmptyOrderForm() {
        browser.findElement(By.cssSelector("input[type='text']")).sendKeys("");
        browser.findElement(By.id("orderSubmitButton")).submit();
    }

    private void fillInAndSubmitOrderForm() {
        clearAllFieldsInOrder();
        browser.findElement(By.id("deliveryName")).sendKeys("Piotr");
        browser.findElement(By.id("deliveryStreet")).sendKeys("Pazia");
        browser.findElement(By.id("deliveryCity")).sendKeys("Wawa");
        browser.findElement(By.id("deliveryState")).sendKeys("pl");
        browser.findElement(By.id("deliveryZip")).sendKeys("129");

        browser.findElement(By.id("ccNumber")).sendKeys("125");
        browser.findElement(By.id("ccExpiration")).sendKeys("12/12");
        browser.findElement(By.id("ccCVV")).sendKeys("362");

        browser.findElement(By.id("orderSubmitButton")).submit();
    }

    private void clearAllFieldsInOrder() {
        browser.findElements(By.tagName("input")).forEach(x->{
            x.clear();
        });
    }

    private void clickBuildAnotherTaco() {
        Assertions.assertEquals(getOrderUrl(),browser.getCurrentUrl());
        browser.findElement(By.linkText("LINK")).click();
        Assertions.assertEquals(getDesignUrl(),browser.getCurrentUrl());
    }

    private void buildAndSubmitATaco(String basic_taco, String... ingredients) {
        for(String i: ingredients){
            browser.findElement(By.cssSelector("input[value='"+i+"']")).click();
        }
        browser.findElement(By.id("taconame")).sendKeys(basic_taco);
        browser.findElement(By.cssSelector("input[type='submit']")).submit();
        Assertions.assertEquals(getOrderUrl(),browser.getCurrentUrl());
    }

    private void assertDesignPageElements() {
        List<WebElement> webElementList = browser.findElement(By.className("grid")).findElements(By.className("ingredient-group"));
        Assertions.assertEquals(5,webElementList.size());

        browser.findElements(By.cssSelector("ingredient-group")).forEach(x->{
            x.findElements(By.tagName("div")).stream().forEach(y->{
                String input = y.findElement(By.tagName("input")).getAttribute("value");
                String span = y.findElement(By.tagName("span")).getText();
                checkIdAndName(input,span);
            });
        });
    }

    private void checkIdAndName(String input, String span) {
        ingredientRepo.findAll().forEach(x->{
            if(x.getId().equals(input)){
                Assertions.assertEquals(x.getName(),span);
            }
        });
    }

    private void clickDesignATaco() {
        Assertions.assertEquals(getHomeUrl(),browser.getCurrentUrl());
        browser.findElement(By.linkText("Link")).click();
        Assertions.assertEquals(getDesignUrl(),browser.getCurrentUrl());
    }

    //helpers url methods
    private String getHomeUrl(){ return "http://localhost:"+port+"/"; }
    private String getDesignUrl(){ return getHomeUrl()+"design"; }
    private String getOrderUrl(){ return getHomeUrl()+"current/orders"; }
}
