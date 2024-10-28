import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class GroupCodeBrewTest {

    @Test
    public void testSearchField() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.zennioptical.com/");

        Thread.sleep(1500);

        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"search-input\"]/div/input"));
        searchField.sendKeys("sunglasses");

        Thread.sleep(1000);

        searchField.sendKeys(Keys.ENTER);

        Thread.sleep(1000);

        WebElement item = driver.findElement(By.xpath("//*[@id=\"main-section\"]/div[1]/div/div/h1"));
        String text = item.getText();

        Assert.assertEquals(text, "Affordable Prescription Sunglasses");

        driver.quit();
    }
    @Test
    public void testMainCheckbox() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/checkbox");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mainCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='rct-checkbox']")));
        mainCheckbox.click();

        List<WebElement> childChekboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

        for (WebElement checkbox : childChekboxes){

            Assert.assertTrue(checkbox.isSelected());
        }
        driver.quit();
    }
    @Test
    public void testFieldName() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement fieldName = driver.findElement(By.xpath("//*[@id=\"firstName\"]"));

        fieldName.sendKeys("Alexey");

        assertEquals("Alexey", fieldName.getAttribute("value"), "The name field should contain 'Alexey'");

        driver.quit();
    }
    @Test
    public void testInvalidEmailInput() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");


        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
        emailField.sendKeys("negativetest");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");


        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
        submitButton.click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(emailField, "border-color", "rgb(255, 0, 0)"));

        String borderColor = emailField.getCssValue("border-color");
        System.out.println("Actual border color: " + borderColor);
        String expectedColor = "rgb(255, 0, 0)";

        assert borderColor.equals(expectedColor) : "Border color is not red as expected!";

        driver.quit();
    }
    @Test
    public void testRadioButtons() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-practice.netlify.app/radiobuttons");

        List<WebElement> radioButtons = driver.findElements(By.className("form-check-input"));
        for (WebElement radioButton : radioButtons) {
            if (!radioButton.isEnabled()) {
                System.out.println("Радио кнопка ID: " + radioButton.getAttribute("id") + " отключена и пропускается.");
                continue;
            }
            radioButton.click();

            String id = radioButton.getAttribute("id");

            Assert.assertTrue(radioButton.isSelected(), "Радио кнопка ID: " + id + " не выбрана.");

            System.out.println("Радио кнопка ID: " + id + " выбрана.");
        }
        driver.quit();
    }

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
        @Test
        public void testRecoverPassword (){
            WebDriver driver = new ChromeDriver();
            driver.get("https://qa-practice.netlify.app/recover-password");
            WebElement emailField = driver.findElement(By.xpath("//input[@id=\"email\"]"));
            emailField.sendKeys("dgffhgjgh0@gmail.com");
            WebElement recoverPasswordButton = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
            recoverPasswordButton.click();
            WebElement confirmationText = driver.findElement(By.xpath("//div[@id=\"content\"]"));
            String actualMessage = confirmationText.getText();
            String expectedMessage = "An email with the new password has been sent ";
            Assert.assertTrue(actualMessage.contains(expectedMessage), "Confirmation text does not match the expected text");
            driver.quit();
        }
        @Test
        public void testAddItemToCard(){
            WebDriver driver = new ChromeDriver();
            driver.get("https://qa-practice.netlify.app/products_list");
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement itemIphone = driver.findElement(By.xpath("//div[@class='shop-items']//div[1]//div[1]//button[1]"));
            itemIphone.click();
            WebElement itemHuawei = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'][normalize-space()='ADD TO CART'])[2]")));
            itemHuawei.click();
            List<WebElement> checkShoppingCard = driver.findElements(By.xpath("//div[@class=\"cart-items\"]"));
            boolean iphoneInCard = false;
            boolean huaweiInCard = false;
            for (WebElement element : checkShoppingCard){
                if (element.getText().contains("iPhone")){
                    iphoneInCard = true;
                }
                if (element.getText().contains("Huawei")){
                    huaweiInCard = true;
                }
            }
            Assert.assertTrue(iphoneInCard, "Item Iphone did not add to card");
            Assert.assertTrue(huaweiInCard, "Item Huawei did not add to card");
            driver.quit();
        }
        @Test
        public void testQApractice() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            driver.get("https://qa-practice.netlify.app/auth_ecommerce");
            WebElement textBox = driver.findElement(By.id("email"));
            textBox.sendKeys("admin@admin.com");

            WebElement textBoxPass = driver.findElement(By.name("password"));
            textBoxPass.sendKeys("admin123");


            WebElement button = driver.findElement(By.id("submitLoginBtn"));
            button.click();

            driver.quit();
        }

    @Test
    public void testVeggieGrillMenu() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.veggiegrill.com/");

        Thread.sleep(1000);

        WebElement popup = driver.findElement(By.xpath("//*[@id=\"popup-alert\"]/button"));
        popup.click();

        Thread.sleep(1000);

        WebElement menuList = driver.findElement(By.xpath("/html/body/header/div[2]/button"));
        menuList.click();

        Thread.sleep(1000);

        WebElement menuButton = driver.findElement(By.xpath("//*[@id=\"SiteHeaderMobilePanel\"]/div[1]/nav/ul/li[2]/a"));
        Assert.assertEquals(menuButton.getText(), "MENUS");
        //menuButton.click();

        driver.quit();
    }

    @Test
    public void testVeggieGrillLocations() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.veggiegrill.com/");

        Thread.sleep(1000);

        WebElement popup = driver.findElement(By.xpath("//*[@id=\"popup-alert\"]/button"));
        popup.click();

        Thread.sleep(1000);

        WebElement menuList = driver.findElement(By.xpath("/html/body/header/div[2]/button"));
        menuList.click();

        Thread.sleep(1000);

        WebElement locationsButton = driver.findElement(By.xpath("//*[@id=\"SiteHeaderMobilePanel\"]/div[1]/nav/ul/li[1]/a"));
        locationsButton.click();

        Thread.sleep(1000);

        WebElement enterCityLabel = driver.findElement(By.xpath("//*[@id=\"locationsContainer\"]/div[1]/label"));
        Assert.assertEquals(enterCityLabel.getText(), "Enter zip code, city, or full address");

        driver.quit();
    }

    @Test
    public void testDropDownMenu () {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://qa-practice.netlify.app/dropdowns");

        WebElement dropDownMenu = webDriver.findElement(By.xpath("//select[@id='dropdown-menu']"));
        Select pickCounty = new Select(dropDownMenu);
        pickCounty.selectByVisibleText("Austria");

        String selectedOption = pickCounty.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, "Austria", "Selected country is not Austria");

        webDriver.quit();
    }

    @Test
    public void testOpenApp() {

    }
}




