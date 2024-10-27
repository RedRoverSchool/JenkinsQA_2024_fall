import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class KhPaulTest {
    private WebDriver driver;

    @BeforeTest
    public void setUpDriver(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeWebDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testRadioButtons(){
        driver.get("https://qa-practice.netlify.app/radiobuttons");

        List<WebElement> radioButtons = driver.findElements(By.xpath("//input[@type='radio']"));
        for (WebElement radioButton : radioButtons) {
            if (!radioButton.isEnabled() || !radioButton.isDisplayed()) {
                System.out.println("Радио кнопка ID: " + radioButton.getAttribute("id") + " отключена или невидима и пропускается.");
                continue;
            }
            radioButton.click();

            String id = radioButton.getAttribute("id");

            Assert.assertTrue(radioButton.isSelected(), "Радио кнопка ID: " + id + " не выбрана.");

            System.out.println("Радио кнопка ID: " + id + " выбрана.");
        }
    }

    @Test
    public void testPurchaseButton() throws InterruptedException {
        driver.get("https://qa-practice.netlify.app/products_list");
        WebElement purchaseButton = driver.findElement(
                By.xpath("//button[contains(@class,'btn')and text()='PURCHASE']"));
        purchaseButton.click();
        Thread.sleep(1000);

        WebElement idText = driver.findElement(By.xpath("//div[@id='message']"));
        Assert.assertEquals(idText.getText(),"Congrats! Your order of $0 has been registered!");
    }

    @Test
    public void testCheckBoxButtons(){
        driver.get("https://qa-practice.netlify.app/checkboxes");

        List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

        for(WebElement checkBox: checkBoxes){
            if(!checkBox.isEnabled() || !checkBox.isDisplayed()){
                System.out.println("Радио кнопка ID: " + checkBox.getAttribute("id") + " отключена или невидима и пропускается.");
                continue;
            }
            checkBox.click();

            String id = checkBox.getAttribute("id");

            Assert.assertTrue(checkBox.isSelected(), "ЧекБокс ID: "+ id + " не выбран");

            System.out.println("ЧекБокс ID: "+ id + " выбран");
        }
    }

    @Test
    public void testResetButtonForCheckBox(){
        driver.get("https://qa-practice.netlify.app/checkboxes");
        SoftAssert softAssert = new SoftAssert();

        List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

        for(WebElement checkBox: checkBoxes){
            if(!checkBox.isEnabled() || !checkBox.isDisplayed()){
                System.out.println("Радио кнопка ID: " + checkBox.getAttribute("id") + " отключена или невидима и пропускается.");
                continue;
            }
            checkBox.click();

            String id = checkBox.getAttribute("id");

            softAssert.assertTrue(checkBox.isSelected(), "ЧекБокс ID: "+ id + " не выбран");

            System.out.println("ЧекБокс ID: "+ id + " выбран");
        }

        WebElement resetButton = driver.findElement(
                By.xpath("//button[@type='reset' and @class='btn btn-primary']"));
        resetButton.click();

        for(WebElement checkBox: checkBoxes){
            if(!checkBox.isEnabled() || !checkBox.isDisplayed()){
                System.out.println("Радио кнопка ID: " + checkBox.getAttribute("id") + " отключена или невидима и пропускается.");
                continue;
            }

            String id = checkBox.getAttribute("id");

            softAssert.assertTrue(
                    !checkBox.isSelected(), "ЧекБокс ID: " + id + " все еще выбран и Reset не сработал");

            System.out.println("ЧекБокс ID: "+ id + " был сброшен");
        }
        softAssert.assertAll();
    }
}
