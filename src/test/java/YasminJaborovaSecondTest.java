import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class YasminJaborovaSecondTest {

    @Test
    public void testCartFunctionality() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //1.Follow the link
        driver.get("https://magento.softwaretestingboard.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //2. Hover over 'Women' dropdown.
        WebElement womenDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ui-id-4\"]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", womenDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(womenDropdown).perform();

        //Hover over 'Tops' button
        WebElement topsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ui-id-9\"]/span[2]")));
        actions.moveToElement(topsButton).perform();

        //Select 'Jackets'
        WebElement jacketsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ui-id-11\"]/span")));
        jacketsButton.click();

        //Select 1 item - jacket
        WebElement item = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol/li[3]/div/div/strong/a")));
        item.click();

        //Select size and color of item, and add it to the cart
        WebElement sizeOfItemL = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"option-label-size-143-item-169\"]")));
        WebElement colorOfItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"option-label-color-93-item-49\"]")));
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-addtocart-button\"]")));
        js.executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
        sizeOfItemL.click();
        colorOfItem.click();
        addToCartButton.click();

        //Go to Shopping cart
        WebElement shoppingCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div/a")));
        shoppingCartButton.click();

        //Proceed to Checkout
        WebElement proceedToCheckoutButton =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/ul/li[1]/button")));
        js.executeScript("arguments[0].scrollIntoView(true);", proceedToCheckoutButton);
        proceedToCheckoutButton.click();

        //Compare current and expected results
        WebElement quantityOfItemsInCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"opc-sidebar\"]/div[1]/div/div[1]/strong/span[1]")));
        Assert.assertEquals(quantityOfItemsInCart.getText(), "1");

        driver.quit();

    }
}
