import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EdGatinTest {
    @Test
    public void test() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://github.com/RedRoverSchool/JenkinsQA_2024_fall");
        driver.findElement(new By.ByXPath("//*[@id=\":R55ab:\"]")).click();
        String url = driver.findElement(By.id("clone-with-https")).getAttribute("value");
        System.out.println(url);
        driver.close();
        Assert.assertEquals(url, "https://github.com/RedRoverSchool/JenkinsQA_2024_fall.git");
    }
}
