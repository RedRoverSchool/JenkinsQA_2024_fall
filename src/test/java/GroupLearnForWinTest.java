import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GroupLearnForWinTest {

    @Test
    public void verifyStandartUserPassword() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.saucedemo.com/");

        driver.manage().window().fullscreen();

        WebElement textBoxUserName = driver.findElement(By.xpath("//*[@id='user-name']"));
        textBoxUserName.sendKeys("standard_user");

        WebElement textBoxPassword = driver.findElement(By.xpath("//*[@id='password']"));
        textBoxPassword.sendKeys("secret_sauce");

        Thread.sleep(2000);

        WebElement buttonLogin = driver.findElement(By.xpath("//*[@id='login-button']"));
        buttonLogin.click();

        Thread.sleep(2000);

        WebElement itemTitle = driver.findElement(By.xpath("//*[@id='header_container']/div[2]/span"));
        Assert.assertEquals(itemTitle.getText(), "Products");

        driver.quit();
    }

    @Test
    public void testFindSubmitNewLanguage() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.99-bottles-of-beer.net/");

        driver.findElement(By.linkText("Submit new Language")).click();
        String submitNewLanguage = driver.findElement(By.linkText("Submit New Language")).getText();


        Assert.assertEquals(submitNewLanguage, "Submit New Language");

        driver.quit();

    }

    @Test
    public void testSubmitLanguageEmptyError() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.99-bottles-of-beer.net/submitnewlanguage.html");

        driver.findElement(By.name("submitlanguage")).click();
        String textError = driver.findElement(By.xpath("//p[contains(text(), ' Precondition failed - Incomplete Input.')]")).getText();

        Assert.assertEquals(textError, "Error: Precondition failed - Incomplete Input.");

        driver.quit();

    }


    @Test
    public void verifyLogout() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.saucedemo.com/");

        driver.manage().window().fullscreen();

        WebElement textBoxUserName = driver.findElement(By.xpath("//*[@id='user-name']"));
        textBoxUserName.sendKeys("standard_user");

        WebElement textBoxPassword = driver.findElement(By.xpath("//*[@id='password']"));
        textBoxPassword.sendKeys("secret_sauce");

        Thread.sleep(2000);

        WebElement buttonLogin = driver.findElement(By.xpath("//*[@id='login-button']"));
        buttonLogin.click();

        Thread.sleep(2000);

        WebElement burgerButton = driver.findElement(By.xpath("//*[@id='react-burger-menu-btn']"));
        burgerButton.click();

        Thread.sleep(2000);

        WebElement logout = driver.findElement(By.xpath("//*[@id='logout_sidebar_link']"));
        logout.click();

        Thread.sleep(2000);

        WebElement loginButtonContainer = driver.findElement(By.xpath("//*[@id='login_button_container']"));
        Assert.assertTrue(loginButtonContainer.isDisplayed());

        driver.quit();

    }

    @Test
    public void testSubMenuItems() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.99-bottles-of-beer.net");
        driver.findElement(By.xpath("//*[@id='menu']/li[4]/a")).click();

        List<String> listOfExpectedSubMenuItems = Arrays.asList(
                "Top Rated",
                "Top Rated Real",
                "Top Rated Esoteric",
                "Top Rated Assembly",
                "Top Hits",
                "New Languages this month",
                "New Comments");
        List<WebElement> listOfDashboardSubMenuItems = driver.findElements(By.xpath("//ul[@id = 'submenu']/li"));
        List<String> extractedTexts = listOfDashboardSubMenuItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        Assert.assertEquals(extractedTexts, listOfExpectedSubMenuItems);

        driver.quit();

    }

}
