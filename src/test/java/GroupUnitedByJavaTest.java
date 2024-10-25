import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupUnitedByJavaTest {

    private static final String URL_SELENIUM_TEST_PAGE = "https://www.selenium.dev/selenium/web/web-form.html";
    private static final String URL_GOOGLE_COM = "https://www.google.com";
    private static final String URL = "https://www.saucedemo.com";

    @Test
    public void testDoubleClick() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");

        WebElement elementsPage = driver.findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = driver.findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMe = driver.findElement(By.id("doubleClickBtn"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickMe).perform();

        String doubleClickMessage = driver.findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessage, "You have done a double click");

        driver.quit();
    }

    @Test
    public void testText() {

        WebDriver driver = new ChromeDriver();
        driver.get(URL_SELENIUM_TEST_PAGE);

        WebElement selectDropdown = driver.findElement(By.name("my-select"));
        selectDropdown.click();

        WebElement selectOption = driver.findElement(By.cssSelector("[value='2']"));
        selectOption.click();

        WebElement submitButton = driver.findElement(By.cssSelector("[type='submit']"));
        submitButton.click();

        WebElement messageText = driver.findElement(By.id("message"));

        Assert.assertEquals(messageText.getText(), "Received!");
        driver.quit();
    }

    @Test
    public void testCheck() {

        WebDriver driver = new ChromeDriver();
        driver.get(URL_SELENIUM_TEST_PAGE);

        WebElement checkInput = driver.findElement(By.id("my-check-2"));
        checkInput.click();

        Assert.assertEquals(checkInput.getDomProperty("checked"), "true");

        driver.quit();
    }

    @Test
    public void testSelectingOption() {

        WebDriver driver = new ChromeDriver();

        driver.get(URL_SELENIUM_TEST_PAGE);

        WebElement dropdownSelect = driver.findElement(By.cssSelector("[name='my-select']"));
        Select select = new Select(dropdownSelect);
        select.selectByVisibleText("Three");

        WebElement threeOption = driver.findElement(By.cssSelector("[value='3']"));
        Assert.assertTrue(threeOption.isSelected());

        driver.quit();
    }

    @Test
    public void testSortingPrice() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        WebElement loginInput = driver.findElement(By.id("user-name"));
        loginInput.sendKeys("standard_user");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement sortingSelect = driver.findElement(By.className("product_sort_container"));
        sortingSelect.click();

        WebElement lohiOption = driver.findElement(By.cssSelector("[value='lohi']"));
        lohiOption.click();

        List<WebElement> priceList = driver.findElements(By.className("inventory_item_price"));

        List<Double> prices = new ArrayList<>();

        for (WebElement priceElement : priceList) {
            String priceText = priceElement.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }

        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        Assert.assertEquals(prices, sortedPrices);

        driver.quit();
    }

    @Test
    public void testTitleGoogle() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String expectedTitle = "Google";

        driver.get(URL_GOOGLE_COM);

        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle);

        driver.quit();
    }

    @Test
    public void testSearchBar() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String expectedURL = "https://www.google.ru/?hl=ru";

        driver.get(URL_GOOGLE_COM);

        WebElement searchBar = driver.findElement(By.xpath("//textarea[@title='Поиск']"));
        searchBar.sendKeys("Google");
        searchBar.sendKeys(Keys.ENTER);

        WebElement lineResponse = driver.findElement(By.xpath("(//h3[.='Google'])[1]"));
        lineResponse.click();

        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        driver.quit();
    }

    @Test
    public void testAddToCardBag() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement setUserName = driver.findElement(By.id("user-name"));
        setUserName.click();
        setUserName.sendKeys("standard_user");

        WebElement setPassword = driver.findElement(By.id("password"));
        setPassword.click();
        setPassword.sendKeys("secret_sauce");

        WebElement buttonLogin = driver.findElement(By.id("login-button"));
        buttonLogin.click();

        WebElement getBag = driver.findElement(By.id("item_4_title_link"));
        getBag.click();

        WebElement buttonAddToCard = driver.findElement(By.name("add-to-cart"));
        buttonAddToCard.click();

        WebElement buttonBasket = driver.findElement(By.className("shopping_cart_link"));
        buttonBasket.click();

        WebElement text = driver.findElement(By.xpath("//*[@data-test=\"inventory-item-name\"]"));

        Assert.assertEquals(text.getText(), "Sauce Labs Backpack");

        driver.quit();
    }

    @Test
    public void testCheckRegisterFields() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        driver.manage().window().fullscreen();

        final List<String> expectedFields = List.of("First Name:", "Last Name:", "Address:",
                "City:", "State:", "Zip Code:", "Phone #:", "SSN:", "Username:", "Password:", "Confirm:");

        List<WebElement> fieldsElements = driver.findElements(By.xpath("//tbody/tr/td/b"));

        List<String> actualFields = fieldsElements.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualFields, expectedFields);

        driver.quit();
    }


    @Test
    public void testLongForm() throws InterruptedException {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://demoqa.com/automation-practice-form");

        driver.findElement(By.id("firstName")).sendKeys("Julia");

        driver.findElement(By.id("lastName")).sendKeys("Ivanova");

        driver.findElement(By.id("userEmail")).sendKeys("juju@mail.com");

        driver.findElement(By.xpath("//*[contains(text(),'Female')]")).click();

        driver.findElement(By.id("userNumber")).sendKeys("78479857847");

        driver.findElement(By.id("dateOfBirthInput")).click();

        WebElement selectMonth = driver.findElement(By.className("react-datepicker__month-select"));
        Select valueMonth = new Select(selectMonth);
        valueMonth.selectByValue("5");

        WebElement selectYear = driver.findElement(By.className("react-datepicker__year-select"));
        Select valueYear = new Select(selectYear);
        valueYear.selectByValue("1994");

        driver.findElement(By.className("react-datepicker__day--019")).click();

        WebElement subjects = driver.findElement(By.id("subjectsInput"));
        subjects.sendKeys("English");
        subjects.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//*[contains(text(),'Reading')]")).click();

        driver.findElement(By.id("currentAddress")).sendKeys("12 Warwickshire Mansions");

        WebElement pagedown = driver.findElement(By.id("currentAddress"));
        pagedown.sendKeys(Keys.PAGE_DOWN);

        Thread.sleep(1000);

        driver.findElement(By.id("submit")).click();

        WebElement finalText = driver.findElement(By.id("example-modal-sizes-title-lg"));
        Assert.assertEquals(finalText.getText(), "Thanks for submitting the form");
        final List<String> expectedFields = List.of("Julia Ivanova", "juju@mail.com", "Female",
                "7847985784", "19 June,1994", "English", "Reading", "", "12 Warwickshire Mansions", "");
        List<WebElement> fieldsElements = driver.findElements(By.xpath("//tbody/tr/td[2]"));
        List<String> actualFields = fieldsElements.stream().map(el -> el.getText()).toList();
        Assert.assertEquals(actualFields, expectedFields);

        driver.quit();
    }

    @Test
    public void testSuccessLogin() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        driver.get(URL);
        Thread.sleep(500);

        WebElement username = driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("standard_user");
        Thread.sleep(500);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement product = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        Assert.assertEquals(product.getText(), "Sauce Labs Backpack");
        driver.quit();
    }

    @Test
    public void testLockedUserLogin() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        driver.get(URL);

        WebElement username = driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("locked_out_user");
        Thread.sleep(500);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement lockedError = driver.findElement(By.xpath("//*[@data-test='error']"));
        Assert.assertEquals(lockedError.getText(), "Epic sadface: Sorry, this user has been locked out.");
        driver.quit();

    }

    @Test
    public void testProblemUserLogin() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        driver.get(URL);

        WebElement username = driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("problem_user");
        Thread.sleep(500);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement headerLabel = driver.findElement(By.xpath("//*[@class='app_logo']"));
        Assert.assertEquals(headerLabel.getText(), "Swag Labs");

        driver.quit();
    }

    @Test
    public void getTitle() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://chatgpt.com/");
        driver.manage().window().maximize();

        System.out.println(driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "ChatGPT");

        driver.quit();

    }

    @Test
    public void checkDialogWindow() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://chatgpt.com/");
        driver.manage().window().maximize();

        WebElement newChatButton = driver.findElement(By.xpath("//span[@class='flex']"));
        newChatButton.click();

        WebElement dialogWindow = driver.findElement(By.xpath("//div[@role='dialog']"));
        Assert.assertTrue(dialogWindow.getText().contains("Clear current chat"));

        driver.quit();

    }

    @Test
    public  void testDemoQaForms () {

        WebDriver driver=new ChromeDriver();
        driver.get("https://demoqa.com/forms");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement firstStep = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[1]/div/div/div[2]/div/ul/li/span"));
        firstStep.click();

        WebElement name = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[1]/div[2]/input"));
        name.sendKeys("Andrey");

        WebElement lastName = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[1]/div[4]/input"));
        lastName.sendKeys("Karavanov");

        WebElement email = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[2]/div[2]/input"));
        email.sendKeys("QWERTY@mail.ru");

        WebElement gender = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[3]/div[2]/div[1]"));
        gender.click();

        WebElement mobileNumber = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[4]/div[2]/input"));
        mobileNumber.sendKeys("8977977977");

        WebElement date = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[5]/div[2]/div[1]/div/input"));
        date.sendKeys(Keys.CONTROL+"a");
        date.sendKeys("19 Apr 1993");
        date.sendKeys(Keys.RETURN);

        WebElement hobby = driver.findElement(By.xpath("//label[text()='Sports']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", hobby);

        WebElement addres = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[9]/div[2]/textarea"));
        addres.sendKeys("улица Пушкина, дом Колотушкина 9-2");

        WebElement state = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[10]/div[2]/div/div/div[1]"));
        state.click();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ENTER).perform();

        WebElement submit = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[11]/div/button"));
        submit.click();

        WebElement message = driver.findElement(By.xpath("//html/body/div[4]/div/div/div[1]/div"));
        Assert.assertEquals(message.getText(), "Thanks for submitting the form");

        driver.quit();

    }
}
