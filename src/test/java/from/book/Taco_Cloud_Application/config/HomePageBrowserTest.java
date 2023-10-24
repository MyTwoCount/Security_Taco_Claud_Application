package from.book.Taco_Cloud_Application.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

    @LocalServerPort
    private int port;

    private static HtmlUnitDriver browser;

    @BeforeAll
    public static void prepareBrowserModel(){
        browser = new HtmlUnitDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    }

    @Test
    public void testHomeControllerWithBrowserModel(){
        browser.get(getHomeUrl());
        String title = browser.getTitle();
        Assertions.assertThat(title).isEqualTo("Home Page Taco");

        String h1Text = browser.findElement(By.tagName("h1")).getText();
        Assertions.assertThat(h1Text).isEqualTo("Hello in Taco Page:");

        String aText = browser.findElement(By.tagName("span")).findElement(By.tagName("a")).getText();
        Assertions.assertThat(aText).isEqualTo("Link");

        String currentUrl = browser.getCurrentUrl();
        Assertions.assertThat(currentUrl).isEqualTo("http://localhost:"+port+"/");
    }

    //Helpful UrlPath methodes
    public String getHomeUrl(){return "http://localhost:"+port+"/";}
}
