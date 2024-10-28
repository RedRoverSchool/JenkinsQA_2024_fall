import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GroupKrutyeBobryTest {
    WebDriver driver = new ChromeDriver();

    @Test
    public void testWaitForLoadingPicture() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        Boolean textInp = wait.until(ExpectedConditions.textToBe(By.id("text"), "Done!"));
        Assert.assertEquals(textInp, true);

        driver.quit();
    }

    @Test
    public void testMouseActionDropDown() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");

        Actions actions = new Actions(driver);

        WebElement dropDown2 = driver.findElement(By.id("my-dropdown-2"));
        WebElement contextMenu2 = driver.findElement(By.id("context-menu-2"));
        actions.contextClick(dropDown2).perform();
        Assert.assertTrue(contextMenu2.isDisplayed());

        WebElement dropDown3 = driver.findElement(By.id("my-dropdown-3"));
        WebElement contextMenu3 = driver.findElement(By.id("context-menu-3"));
        actions.doubleClick(dropDown3).perform();
        Assert.assertTrue(contextMenu3.isDisplayed());

        WebElement dropDown1 = driver.findElement(By.id("my-dropdown-1"));
        actions.click(dropDown1).perform();

        WebElement contextMenu1 = driver.findElement(By.xpath("//ul[@class='dropdown-menu show']"));
        Assert.assertTrue(contextMenu1.isDisplayed());

        driver.quit();
    }

    @Test
    public void testFindPageTitleHeadless(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://magento.softwaretestingboard.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Home Page");
        driver.quit();
    }

    @Test
    public void testGoCatalogByImageLink(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com/");
        WebElement link = driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/a"));

        String url = "https://magento.softwaretestingboard.com/collections/yoga-new.html";
        link.click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, url);
        driver.quit();
    }

    // --- SauceDemo Tests ---
    public void loginSauce(String username, String password){
        String url = "https://www.saucedemo.com";
        driver.get(url);

        WebElement usernameField = driver.findElement(By.xpath("//input[@id='user-name']"));
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passField = driver.findElement(By.xpath("//input[@id='password']"));
        passField.clear();
        passField.sendKeys(password);

        driver.findElement(By.xpath("//input[@id='login-button']")).click();
    }
    @Test(groups = "saucedemo")
    public void testNotEnterLockedUser() {
        String password = "secret_sauce";
        String locked_username = "locked_out_user";
        String lockedMsg = "Epic sadface: Sorry, this user has been locked out.";

        loginSauce(locked_username, password);

        String actualMsg = driver.findElement(By.xpath("//div[@class='error-message-container error']")).getText();

        Assert.assertEquals(actualMsg, lockedMsg);
    }

    @Test(groups = "saucedemo")
    public void testWrongCredentials(){
        String password = "secret_sauce";
        String wrongUsername = "@($*%^)$(%^*$";
        String wrongCredsMsg = "Epic sadface: Username and password do not match any user in this service";

        loginSauce(wrongUsername, password);
        String actualMsg = driver.findElement(By.xpath("//div[@class='error-message-container error']")).getText();

        Assert.assertEquals(actualMsg, wrongCredsMsg);
    }

    @Test(groups = "saucedemo")
    public void testSuccessEnter() {
        String password = "secret_sauce";
        String username = "standard_user";

        loginSauce(username, password);
        WebElement actual = driver.findElement(By.xpath("//span[@data-test='title']"));

        Assert.assertEquals(actual.getText(), "Products");
    }

    @Test(dependsOnMethods = "testSuccessEnter", groups = "saucedemo")
    public void testSortByPrice(){
        loginSauce("standard_user", "secret_sauce");

        WebElement sorter = driver.findElement(By.xpath("//select[@class='product_sort_container']"));
        sorter.click();
        driver.findElement(By.xpath("//option[@value='lohi']")).click();

        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        double first = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        double last = Double.parseDouble(prices.get(prices.size() - 1).getText().replace("$", ""));
        Assert.assertTrue(first < last);

        sorter = driver.findElement(By.className("product_sort_container"));
        sorter.click();
        driver.findElement(By.xpath("//option[@value='hilo']")).click();
        prices = driver.findElements(By.className("inventory_item_price"));
        first = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        last = Double.parseDouble(prices.get(prices.size() - 1).getText().replace("$", ""));
        Assert.assertTrue(first > last);
    }

    @Test(dependsOnMethods = "testSuccessEnter", groups = "saucedemo")
    public void testAddAndRemoveCart(){
        loginSauce("standard_user", "secret_sauce");

        List<WebElement> buttons = driver.findElements(By.className("btn_inventory"));
        for (WebElement button : buttons) {
            button.click();
        }
        WebElement cartBadge = driver.findElement(By.xpath("//span[@data-test='shopping-cart-badge']"));
        Assert.assertEquals(Integer.parseInt(cartBadge.getText()), buttons.size());

        List<WebElement> removeButtons = driver.findElements(By.className("btn_inventory"));
        for (int i = removeButtons.size() - 1; i > 2; i--) {
            removeButtons.get(i).click();
        }
        Assert.assertEquals(Integer.parseInt(cartBadge.getText()), 3);
    }

    @Test(dependsOnMethods = "testSuccessEnter", groups = "saucedemo")
    public void testOpeningCart(){
        loginSauce("standard_user", "secret_sauce");

        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
        String expectUrl = "https://www.saucedemo.com/cart.html";
        Assert.assertEquals(driver.getCurrentUrl(),expectUrl);
    }

    @AfterGroups("saucedemo")
    public void tearDown(){
        driver.quit();
    }
}
