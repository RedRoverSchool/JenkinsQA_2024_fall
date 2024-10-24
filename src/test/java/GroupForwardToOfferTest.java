import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

public class GroupForwardToOfferTest {
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeTest
    public void beforTest() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
    @Test
    public void testA1WinterSemesterPrice(){
        driver.get("https://www.srpski-strani.com/pocetninivo1_eng.php");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement textPrice1 = driver.findElement(By.xpath("/html/body/div/div[3]/div/table[2]/tbody/tr[1]/td[4]"));
        String foundResult = textPrice1.getText();
        Assert.assertEquals(foundResult, "26.400 RSD (224 €)");
    }
    @Test
    public void testMainMenuA1Program() {

        driver.get("https://www.srpski-strani.com/index_eng.php");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement menuProgramme = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div/div/ul/li[3]/a"));
        Actions action = new Actions(driver);
        action.moveToElement(menuProgramme);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement menuA1Program = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div/div/ul/li[3]/ul/li[1]/a"));
        action.perform();
        menuA1Program.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement result = driver.findElement(By.xpath("/html/body/div/div[3]/div/p[1]"));
        result.getText();
        Assert.assertEquals(result.getText(), "AIM AND SYLLABUS");
    }


    @Test //(priority = 1)
    public void testGarfild() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        driver.get("https://garfield.by/?utm_source=google&utm_medium=cpc&utm_campaign=17619287198&utm_content=&utm_term=&gad_source=1&gclid=CjwKCAjw9p24BhB_EiwA8ID5BlycoY_gj8tdIObtHyJdlX0EleOFbNVrF1vokbRJm7b6uhC1n2gh9hoCTCwQAvD_BwE");
        driver.findElement(By.xpath("//a[contains(text(),'Щенки')]")).click();

        driver.findElement(By.xpath("//div[@class='js-accordion-wrap active']//a[@class='catalog-nav-links__item link'][contains(text(),'Сухие корма')]")).click();
        driver.findElement(By.xpath("//div[@class='manufacturer__default']//p[@class='catalog-filter-content-checkbox__label'][normalize-space()='Grandorf']")).click();

        WebElement filter = driver.findElement(By.id("line_arrFilterCat_124_3205278765"));
        Assert.assertEquals(filter.getText(), "Grandorf");
    }


    @Test //(priority = 2)
    public void testRunin() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
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



    @Test //(priority = 3)
    public void testKufar() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
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

    @Test
    public void test() {

        driver.get("https://ru.pinterest.com/");
        driver.manage().window().maximize();
        //находит и нажимает на "Посмотреть"
        WebElement web = driver.findElement(By.xpath("//button[@class='RCK Hsu USg adn gn8 L4E kVc CCY oRi lnZ wsz']//div[1][@class='X8m tg7 tBJ dyH iFc sAJ H2s']"));
        clickWhenVisible(By.xpath("//button[@class='RCK Hsu USg adn gn8 L4E kVc CCY oRi lnZ wsz']//div[1][@class='X8m tg7 tBJ dyH iFc sAJ H2s']"));

        //находит и нажимает на категорию с животными
        clickWhenVisible(By.xpath("//h3[text()='Животные']"));

        //находит пин с уткой
        clickWhenVisible(By.xpath("//div[@id='mweb-unauth-container']//article[2]//a[.//img[contains(@src, 'f6655347abfddc935a854b2b192a2c3d.jpg')]]"));

        //порверить текст пина
        WebElement pinText = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(pinText.getText(), "25+ фото с животными, которые согреют даже в самый морозный день");

    }
    private WebElement waitUntilVisible(By locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }

    private void clickWhenVisible(By locator) {
        WebElement element = waitUntilVisible(locator);
        element.click();
    }

    @Test
    public void testNumberOfCarsPresented() {
        driver.get("https://av.by/");
        //нажать на кнопку отказатся во всплывающем окне про cookies
        driver.findElement(By.xpath("//button[@class='button button--default button--block button--large']")).click();
        ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(0,500)");
        //собрать веб элементы которые обозначают марки машин в лист
        List<WebElement> carsModels = driver.findElements(By.xpath("//span[@class='catalog__title']"));
        Assert.assertEquals(carsModels.size(), 30);
    }


    @AfterTest
    public void afterTest(){
        //driver.quit();
    }
}
