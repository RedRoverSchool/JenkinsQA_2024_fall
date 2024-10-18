import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class NataliaKaluginaTest {
    @Test
    public void testA1WinterSemesterPrice(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.srpski-strani.com/pocetninivo1_eng.php");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textPrice1 = driver.findElement(By.xpath("/html/body/div/div[3]/div/table[2]/tbody/tr[1]/td[4]"));
        String foundResult = textPrice1.getText();
        Assert.assertEquals(foundResult, "26.400 RSD (224 €)");

        driver.quit();
    }
    @Test
    public void testMainMenuA1Program() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.srpski-strani.com/index_eng.php");
        driver.manage().window().maximize();
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

        driver.quit();
    }

}
