import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class DianaKTest {

    WebDriver driver = new ChromeDriver();

    @BeforeTest
    public void avto(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void clouse(){
        driver.quit();
    }


    @Test
    public void testGarfild() {

        driver.get("https://garfield.by/?utm_source=google&utm_medium=cpc&utm_campaign=17619287198&utm_content=&utm_term=&gad_source=1&gclid=CjwKCAjw9p24BhB_EiwA8ID5BlycoY_gj8tdIObtHyJdlX0EleOFbNVrF1vokbRJm7b6uhC1n2gh9hoCTCwQAvD_BwE");
        driver.findElement(By.className("icon-open")).click();

        driver.findElement(By.partialLinkText("Для щенков")).click();
        driver.findElement(By.partialLinkText("Сухие корма")).click();
        driver.findElement(By.xpath("//div[@class='manufacturer__default']//p[@class='catalog-filter-content-checkbox__label'][normalize-space()='Grandorf']")).click();

        WebElement filter = driver.findElement(By.id("line_arrFilterCat_124_3205278765"));
        Assert.assertEquals(filter.getText(), "Grandorf");
    }


    @Test
    public void testRunin() {
        driver.get("https://runin.by/");
        driver.findElement(By.xpath("//*[@id=\"sub-header\"]/div[2]/div/div/a[1]")).click();

        WebElement search = driver.findElement(By.name("search"));
        search.click();
        search.sendKeys("Минский полумарафон");

        driver.findElement(By.name("allSeasons")).click();
        driver.findElement(By.xpath("//*[@id=\"app\"]/main/section/div/div/div[1]/div[1]/div[1]/div[3]/div/div[2]/div[2]/div/div/div/div[5]/div/div[1]/a/div/div[1]")).click();

        driver.findElement(By.xpath("//*[@id=\"app\"]/div[5]/div/div[2]/div/div/button")).click();

        driver.findElement(By.xpath("//div[@class='event-intro__actions']//button[@type='button']")).click();
        driver.findElement(By.xpath("//*[@id=\"app\"]/main/div[8]/div[2]/div/div[2]/div/table/tbody/tr[1]/td[4]/div/a\n")).click();

        WebElement distance21 = driver.findElement(By.partialLinkText("Результаты"));
        Assert.assertEquals(distance21.getText(),"Результаты");
    }



    @Test
    public void testKufar() {

        driver.get("https://re.kufar.by/");
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div/div/div/div/button")).click();
        driver.findElement(By.id("let_long")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Цена за месяц')]")).click();

        WebElement moneymax = driver.findElement(By.xpath("//input[@id='prc.upper']"));
        moneymax.click();
        moneymax.sendKeys("500");

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement name = driver.findElement(By.xpath("//h1[contains(text(),'Аренда квартир на длительный срок в Минске')]"));
        Assert.assertEquals(name.getText(),"Аренда квартир на длительный срок в Минске");
    }
}
