import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class YasminJaborovaTest {

    @DataProvider(name = "userData")
    public Object[][] getUserData(){
        List<String[]> users = Arrays.asList(
                new String[]{"standard_user", "secret_sauce"},
                new String[]{"problem_user", "secret_sauce"},
                new String[]{"error_user", "secret_sauce"},
                new String[]{"visual_user", "secret_sauce"}
        );
        return users.toArray(new Object[users.size()][]);
    }

    @Test (dataProvider = "userData")
    public void testAuthorizationWithMultipleUsers(String username, String password) throws InterruptedException {
        //1.Launch new tab in browser
        WebDriver driver = new ChromeDriver();
        //2.Follow the link
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        //3.Fill out all required fields
        WebElement usernameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        Thread.sleep(1000);
        loginButton.click();

        //4.Compare current and expected behaviors
        WebElement headerOfPage = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
        Assert.assertEquals(headerOfPage.getText(), "Products");

        driver.quit();

        }

}
