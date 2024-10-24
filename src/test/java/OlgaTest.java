import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class OlgaTest {

    WebDriver driver = new ChromeDriver();
    @BeforeMethod
    public void BaseURL() {

        driver.get("https://beta.chelznak.ru/made/ ");
        driver.manage().window().maximize();
    }
    @Test
    public void testTitle() {
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Изготовление наград на заказ в ТПП Челзнак");
    }

    @Test
    public void test2(){

        WebElement textBox = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/div/div/div[1]/div/form/input"));
        textBox.sendKeys("Награда МВД");
        textBox.sendKeys(Keys.ENTER);

        String AssertMVD = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div[1]/h1")).getText();
        Assert.assertEquals(AssertMVD,"РЕЗУЛЬТАТОВ ПО ЗАПРОСУ «НАГРАДА МВД»: 235");
    }
    @Test
    public void test3() throws InterruptedException {
        WebElement imagePhone = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/div/div/div[2]/div[2]/a[1]"));
        imagePhone.click();
        Thread.sleep(1000);

        String QuestionsAnswers = driver.findElement(By.xpath("//*[@id='modal-faq']/div[1]/div")).getText();
        Assert.assertEquals(QuestionsAnswers,"ВОПРОСЫ И ОТВЕТЫ");
    }

    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }
}
