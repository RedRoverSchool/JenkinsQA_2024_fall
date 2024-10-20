import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ArtsiomATest {

    @Test
    public void testStrategArendaPomewenii() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://strateg.by");
        Thread.sleep(500);

        WebElement submitArendaPomewenii = driver.findElement(By.xpath("//*[@class= 't770__bottomwrapper t-align_center']//a[contains(text(), 'Аренда помещений')]"));
        submitArendaPomewenii.click();
        Thread.sleep(1000);

        WebElement mfkStrateg01 = driver.findElement(By.xpath("//*[@data-tab-rec-ids='793111509']"));
        Assert.assertEquals(mfkStrateg01.getText(), "МФК СТРАТЕГ 01\n" +
                "ул.Янки Лучины 5");

        driver.quit();
    }

    @Test
    public void testStrategObekti() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://strateg.by");
        Thread.sleep(500);

        WebElement submitMenuButton = driver.findElement(By.xpath("//button[@aria-label='Навигационное меню']"));
        submitMenuButton.click();
        Thread.sleep(200);

        WebElement submitObektiButton = driver.findElement(By.xpath
                ("//*[@class= 't770 t770__positionabsolute tmenu-mobile__menucontent_hidden']//nav[@class='t770__listwrapper t770__mobilelist']//a[contains(text(), 'Объекты')]"));
        submitObektiButton.click();
        Thread.sleep(200);

        WebElement mfkStrateg01 = driver.findElement(By.xpath("//*[@id='cardtitle1_793111500']"));
        Assert.assertEquals(mfkStrateg01.getText(),
                "МНОГОФУНКЦИОНАЛЬНЫЙ\nКУЛЬТУРНО-РАЗВЛЕКАТЕЛЬНЫЙ\nКОМПЛЕКС СТРАТЕГ 01\nг.Минск, ул. Янки Лучины , дома 5 и 7");

        driver.quit();
    }

    @Test
    public void testStrategLocation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://strateg.by");
        Thread.sleep(500);

        Assert.assertEquals(driver.getTitle(), "Коммерческая недвижимость в Минске – Аренда офисов и складов | Стратег");
        WebElement buttonLocation = driver.findElement(By.xpath("//*[@class = 't770__bottomwrapper t-align_center']//li[@class='t770__list_item']//a[contains(text(), 'Расположение')]"));
        buttonLocation.click();
        Thread.sleep(1000);

        WebElement ymaps = driver.findElement(By.xpath("//ymaps[@class = 'ymaps-2-1-79-balloon__content']//ymaps//ymaps"));
        Assert.assertEquals(ymaps.getText(),
                "МФК СТРАТЕГ-01\nг.Минск, ул. Янки Лучины, 5\nг.Минск, ул. Янки Лучины, 7"
                );
        driver.quit();
    }

    @Test
    public void testContactMethods() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://strateg.by");
        Thread.sleep(500);

        Assert.assertEquals(driver.getTitle(), "Коммерческая недвижимость в Минске – Аренда офисов и складов | Стратег");
        WebElement chatButton = driver.findElement(By.xpath("//div[@class='t898__btn']"));
        chatButton.click();

        WebElement phoneButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-phone_wrapper t898__icon_link']"));
        Assert.assertTrue(phoneButton.isDisplayed());

        WebElement viberButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-viber_wrapper t898__icon_link']"));
        Assert.assertTrue(viberButton.isDisplayed());

        WebElement telegramButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-telegram_wrapper t898__icon_link']"));
        Assert.assertTrue(telegramButton.isDisplayed());

        WebElement whatsAppButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-whatsapp_wrapper t898__icon_link']"));
        Assert.assertTrue(whatsAppButton.isDisplayed());

        driver.quit();
    }
}
