import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AlexMotoTest {
    @Test
    public void testMyBag (){
        WebDriver driver = new ChromeDriver();
        driver.get("https://us.sportsdirect.com/");
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement acceptCookie = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            acceptCookie.click();
        } catch (TimeoutException e){
            System.out.println("Cookie accept button not found or already accepted.");
        }
        WebElement myBagButton = driver.findElement(By.xpath("//a[@id='aBagLink']"));
        Assert.assertTrue(myBagButton.isDisplayed(), "My button should displayed");
        driver.quit();
    }
    @Test
    public void testLinkHomePage () throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        WebElement elementsButton = driver.findElement(By.xpath("//div[@class='category-cards']//div[1]//div[1]//div[1]"));
        elementsButton.click();
        WebElement linksButton = driver.findElement(By.xpath("//li[@id=\"item-5\"]"));
        linksButton.click();
        WebElement homePageLink = driver.findElement(By.xpath("//a[@id=\"simpleLink\"]"));
        homePageLink.click();
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()){
            if (!windowHandle.equals(originalWindow)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://demoqa.com/", "Current URL doesn't meet expectations");
        Thread.sleep(3000);
        driver.quit();
    }
    @Test
    public void testCheckBox() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-practice.netlify.app/checkboxes");
        List<WebElement> checkBoxes = driver.findElements(By.xpath("//div[@class='form-group']//input[@type='checkbox']"));
        for (WebElement checkbox : checkBoxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        for (WebElement checkBox : checkBoxes) {
            Assert.assertTrue(checkBox.isSelected(), "Checkbox ID :" + checkBox.getAttribute("ID :" + " is not selected"));
        } driver.quit();
    }
    @Test
    public void radioButtonTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-practice.netlify.app/radiobuttons");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> radioButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@id='content']//input[@type='radio']")));

        for (int i = 0; i < radioButtons.size(); i++) {
            WebElement radioButton = radioButtons.get(i);
            if (radioButton.getAttribute("disabled") != null) {
                System.out.println("Radio button " + (i + 1) + " is disabled and cannot be selected.");
                continue;
            }

            radioButton.click();


            Assert.assertTrue(radioButton.isSelected(), "Radio button " + (i + 1) + " is not selected as expected.");

            for (int j = 0; j < radioButtons.size(); j++) {
                if (j != i) {
                    Assert.assertFalse(radioButtons.get(j).isSelected(), "Radio button " + (j + 1) + " is incorrectly selected.");
                }
            }
        }
        driver.quit();
    }
}
