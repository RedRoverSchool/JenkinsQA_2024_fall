import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DianaKTest {
    @Test
    public void testGarfild() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://garfield.by/?utm_source=google&utm_medium=cpc&utm_campaign=17619287198&utm_content=&utm_term=&gad_source=1&gclid=CjwKCAjw9p24BhB_EiwA8ID5BlycoY_gj8tdIObtHyJdlX0EleOFbNVrF1vokbRJm7b6uhC1n2gh9hoCTCwQAvD_BwE");

        WebElement menu = driver.findElement(By.className("icon-open"));
        menu.click();
        Thread.sleep(500);

        WebElement puppies = driver.findElement(By.partialLinkText("Для щенков"));
        puppies.click();

        WebElement food = driver.findElement(By.partialLinkText("Сухие корма"));
        food.click();
        Thread.sleep(500);

        WebElement grandorf = driver.findElement(By.xpath("//div[@class='manufacturer__default']//p[@class='catalog-filter-content-checkbox__label'][normalize-space()='Grandorf']"));
        grandorf.click();
        Thread.sleep(1000);

        WebElement filter = driver.findElement(By.id("line_arrFilterCat_124_3205278765"));
        Assert.assertEquals(filter.getText(), "Grandorf");

        driver.quit();
    }


    @Test
    public void testRunin() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://runin.by/");

        WebElement find = driver.findElement(By.xpath("//*[@id=\"sub-header\"]/div[2]/div/div/a[1]"));
        find.click();

        WebElement search = driver.findElement(By.name("search"));
        search.click();
        search.sendKeys("Минский полумарафон");
        Thread.sleep(500);

        WebElement checkbox = driver.findElement(By.name("allSeasons"));
        checkbox.click();
        Thread.sleep(2000);

        WebElement param = driver.findElement(By.xpath("//*[@id=\"app\"]/main/section/div/div/div[1]/div[1]/div[1]/div[3]/div/div[2]/div[2]/div/div/div/div[5]/div/div[1]/a/div/div[1]"));
        param.click();
        Thread.sleep(500);

        WebElement cookie = driver.findElement(By.xpath("//*[@id=\"app\"]/div[5]/div/div[2]/div/div/button"));
        cookie.click();
        Thread.sleep(1500);

        WebElement button = driver.findElement(By.xpath("//div[@class='event-intro__actions']//button[@type='button']"));
        button.click();

        WebElement result = driver.findElement(By.xpath("//*[@id=\"app\"]/main/div[8]/div[2]/div/div[2]/div/table/tbody/tr[1]/td[4]/div/a\n"));
        result.click();
        Thread.sleep(2000);

        WebElement distance21 = driver.findElement(By.partialLinkText("Результаты"));
        Assert.assertEquals(distance21.getText(),"Результаты");

        driver.quit();
    }



    @Test
    public void testKufar() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://re.kufar.by/");

        WebElement cookie = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[3]/div/div/div/div/button"));
        cookie.click();

        WebElement appart = driver.findElement(By.id("let_long"));
        appart.click();

        WebElement money = driver.findElement(By.xpath("//button[contains(text(),'Цена за месяц')]"));
        money.click();

        WebElement moneymax = driver.findElement(By.xpath("//input[@id='prc.upper']"));
        moneymax.click();
        money.sendKeys("500");
        Thread.sleep(1000);

        WebElement search = driver.findElement(By.xpath("//button[@type='submit']"));
        search.click();
        Thread.sleep(1000);

        WebElement name = driver.findElement(By.xpath("//h1[contains(text(),'Аренда квартир на длительный срок в Минске')]"));
        Assert.assertEquals(name.getText(),"Аренда квартир на длительный срок в Минске");

        driver.quit();
    }
}
