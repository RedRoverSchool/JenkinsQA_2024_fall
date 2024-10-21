import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RamisTest {
    @Test
    public void testSearchTea() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://magnit.ru/");
        Thread.sleep(1000);

        WebElement search = driver.findElement(By.xpath("//*[@id='magnit-root']/div/header/div[3]/div/div[1]/div[2]/form/div[1]/div/div/input"));
        search.sendKeys("чай");
        Thread.sleep(1000);

        WebElement firstOption = driver.findElement(By.xpath("//*[@id='magnit-root']/div/header/div[3]/div/div[1]/div[2]/div/div/div/a[1]"));
        firstOption.click();
        Thread.sleep(1000);

        //WebElement descriptionTitle = driver.findElement(By.xpath("//*[@id='magnit-root']/div/div/main/div/div[1]/section/div/div/div/div[2]/section[2]/div[2]/div[2]/div"));
        //Assert.assertEquals(descriptionTitle.getText(), "Описание");
        driver.quit();
    }

}




