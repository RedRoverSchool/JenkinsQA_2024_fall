package school.redrover.old;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Ignore
public class GroupUnitedByJavaTest {

    private static final String URL_SELENIUM_TEST_PAGE="https://www.selenium.dev/selenium/web/web-form.html";
    private static final String URL_GOOGLE_COM="https://www.google.com";
    private static final String URL="https://www.saucedemo.com";

    @Test
    public void testDoubleClick() {
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver=new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");

        WebElement elementsPage=driver.findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons=driver.findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMe=driver.findElement(By.id("doubleClickBtn"));
        Actions actions=new Actions(driver);
        actions.doubleClick(doubleClickMe).perform();

        String doubleClickMessage=driver.findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessage, "You have done a double click");

        driver.quit();
    }

    @Test
    public void testText() {

        WebDriver driver=new ChromeDriver();
        driver.get(URL_SELENIUM_TEST_PAGE);

        WebElement selectDropdown=driver.findElement(By.name("my-select"));
        selectDropdown.click();

        WebElement selectOption=driver.findElement(By.cssSelector("[value='2']"));
        selectOption.click();

        WebElement submitButton=driver.findElement(By.cssSelector("[type='submit']"));
        submitButton.click();

        WebElement messageText=driver.findElement(By.id("message"));

        Assert.assertEquals(messageText.getText(), "Received!");
        driver.quit();
    }

    @Test
    public void testCheck() {

        WebDriver driver=new ChromeDriver();
        driver.get(URL_SELENIUM_TEST_PAGE);

        WebElement checkInput=driver.findElement(By.id("my-check-2"));
        checkInput.click();

        Assert.assertEquals(checkInput.getDomProperty("checked"), "true");

        driver.quit();
    }

    @Test
    public void testSelectingOption() {

        WebDriver driver=new ChromeDriver();

        driver.get(URL_SELENIUM_TEST_PAGE);

        WebElement dropdownSelect=driver.findElement(By.cssSelector("[name='my-select']"));
        Select select=new Select(dropdownSelect);
        select.selectByVisibleText("Three");

        WebElement threeOption=driver.findElement(By.cssSelector("[value='3']"));
        Assert.assertTrue(threeOption.isSelected());

        driver.quit();
    }

    @Test
    public void testSortingPrice() {

        WebDriver driver=new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        WebElement loginInput=driver.findElement(By.id("user-name"));
        loginInput.sendKeys("standard_user");

        WebElement passwordInput=driver.findElement(By.id("password"));
        passwordInput.sendKeys("secret_sauce");

        WebElement loginButton=driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement sortingSelect=driver.findElement(By.className("product_sort_container"));
        sortingSelect.click();

        WebElement lohiOption=driver.findElement(By.cssSelector("[value='lohi']"));
        lohiOption.click();

        List <WebElement> priceList=driver.findElements(By.className("inventory_item_price"));

        List <Double> prices=new ArrayList <>();

        for (WebElement priceElement : priceList) {
            String priceText=priceElement.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }

        List <Double> sortedPrices=new ArrayList <>(prices);
        Collections.sort(sortedPrices);

        Assert.assertEquals(prices, sortedPrices);

        driver.quit();
    }

    @Test
    public void testTitleGoogle() {
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();

        String expectedTitle="Google";

        driver.get(URL_GOOGLE_COM);

        String actualTitle=driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle);

        driver.quit();
    }

    @Test
    public void testSearchBar() {
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();

        String expectedURL="https://www.google.ru/?hl=ru";

        driver.get(URL_GOOGLE_COM);

        WebElement searchBar=driver.findElement(By.xpath("//textarea[@title='Поиск']"));
        searchBar.sendKeys("Google");
        searchBar.sendKeys(Keys.ENTER);

        WebElement lineResponse=driver.findElement(By.xpath("(//h3[.='Google'])[1]"));
        lineResponse.click();

        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

        driver.quit();
    }

    @Test
    public void testAddToCardBag() {
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement setUserName=driver.findElement(By.id("user-name"));
        setUserName.click();
        setUserName.sendKeys("standard_user");

        WebElement setPassword=driver.findElement(By.id("password"));
        setPassword.click();
        setPassword.sendKeys("secret_sauce");

        WebElement buttonLogin=driver.findElement(By.id("login-button"));
        buttonLogin.click();

        WebElement getBag=driver.findElement(By.id("item_4_title_link"));
        getBag.click();

        WebElement buttonAddToCard=driver.findElement(By.name("add-to-cart"));
        buttonAddToCard.click();

        WebElement buttonBasket=driver.findElement(By.className("shopping_cart_link"));
        buttonBasket.click();

        WebElement text=driver.findElement(By.xpath("//*[@data-test=\"inventory-item-name\"]"));

        Assert.assertEquals(text.getText(), "Sauce Labs Backpack");

        driver.quit();
    }

    @Test
    public void testCheckRegisterFields() {

        WebDriver driver=new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        driver.manage().window().fullscreen();

        final List <String> expectedFields=List.of("First Name:", "Last Name:", "Address:",
                "City:", "State:", "Zip Code:", "Phone #:", "SSN:", "Username:", "Password:", "Confirm:");

        List <WebElement> fieldsElements=driver.findElements(By.xpath("//tbody/tr/td/b"));

        List <String> actualFields=fieldsElements.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualFields, expectedFields);

        driver.quit();
    }


    @Test
    public void testLongForm() throws InterruptedException {

        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver=new ChromeDriver(chromeOptions);

        driver.get("https://demoqa.com/automation-practice-form");

        driver.findElement(By.id("firstName")).sendKeys("Julia");

        driver.findElement(By.id("lastName")).sendKeys("Ivanova");

        driver.findElement(By.id("userEmail")).sendKeys("juju@mail.com");

        driver.findElement(By.xpath("//*[contains(text(),'Female')]")).click();

        driver.findElement(By.id("userNumber")).sendKeys("78479857847");

        driver.findElement(By.id("dateOfBirthInput")).click();

        WebElement selectMonth=driver.findElement(By.className("react-datepicker__month-select"));
        Select valueMonth=new Select(selectMonth);
        valueMonth.selectByValue("5");

        WebElement selectYear=driver.findElement(By.className("react-datepicker__year-select"));
        Select valueYear=new Select(selectYear);
        valueYear.selectByValue("1994");

        driver.findElement(By.className("react-datepicker__day--019")).click();

        WebElement subjects=driver.findElement(By.id("subjectsInput"));
        subjects.sendKeys("English");
        subjects.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//*[contains(text(),'Reading')]")).click();

        driver.findElement(By.id("currentAddress")).sendKeys("12 Warwickshire Mansions");

        WebElement pagedown=driver.findElement(By.id("currentAddress"));
        pagedown.sendKeys(Keys.PAGE_DOWN);

        Thread.sleep(1000);

        driver.findElement(By.id("submit")).click();

        WebElement finalText=driver.findElement(By.id("example-modal-sizes-title-lg"));
        Assert.assertEquals(finalText.getText(), "Thanks for submitting the form");
        final List <String> expectedFields=List.of("Julia Ivanova", "juju@mail.com", "Female",
                "7847985784", "19 June,1994", "English", "Reading", "", "12 Warwickshire Mansions", "");
        List <WebElement> fieldsElements=driver.findElements(By.xpath("//tbody/tr/td[2]"));
        List <String> actualFields=fieldsElements.stream().map(el -> el.getText()).toList();
        Assert.assertEquals(actualFields, expectedFields);

        driver.quit();
    }

    @Test
    public void testSuccessLogin() throws InterruptedException {
        ChromeDriver driver=new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        Thread.sleep(500);

        WebElement username=driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("standard_user");
        Thread.sleep(500);

        WebElement password=driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton=driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement product=driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        Assert.assertEquals(product.getText(), "Sauce Labs Backpack");
        driver.quit();
    }

    @Test
    public void testLockedUserLogin() throws InterruptedException {
        ChromeDriver driver=new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();

        WebElement username=driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("locked_out_user");
        Thread.sleep(500);

        WebElement password=driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton=driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement lockedError=driver.findElement(By.xpath("//*[@data-test='error']"));
        Assert.assertEquals(lockedError.getText(), "Epic sadface: Sorry, this user has been locked out.");
        driver.quit();

    }

    @Test
    public void testProblemUserLogin() throws InterruptedException {
        ChromeDriver driver=new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();

        WebElement username=driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("problem_user");
        Thread.sleep(500);

        WebElement password=driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton=driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement headerLabel=driver.findElement(By.xpath("//*[@class='app_logo']"));
        Assert.assertEquals(headerLabel.getText(), "Swag Labs");

        driver.quit();
    }

    @Test
    public void getTitle() {
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--no-sandbox");
        WebDriver driver=new ChromeDriver(options);
        driver.get("https://chatgpt.com/");
        driver.manage().window().maximize();

        System.out.println(driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "ChatGPT");

        driver.quit();

    }

    @Test
    public void checkDialogWindow() {
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--no-sandbox");
        WebDriver driver=new ChromeDriver(options);
        driver.get("https://chatgpt.com/");
        driver.manage().window().maximize();

        WebElement newChatButton=driver.findElement(By.xpath("//span[@class='flex']"));
        newChatButton.click();

        WebElement dialogWindow=driver.findElement(By.xpath("//div[@role='dialog']"));
        Assert.assertTrue(dialogWindow.getText().contains("Clear current chat"));

        driver.quit();

    }

    @Test
    public void testDemoQaForms() {

        WebDriver driver=new ChromeDriver();
        driver.get("https://demoqa.com/forms");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement firstStep=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[1]/div/div/div[2]/div/ul/li/span"));
        firstStep.click();

        WebElement name=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[1]/div[2]/input"));
        name.sendKeys("Andrey");

        WebElement lastName=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[1]/div[4]/input"));
        lastName.sendKeys("Karavanov");

        WebElement email=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[2]/div[2]/input"));
        email.sendKeys("QWERTY@mail.ru");

        WebElement gender=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[3]/div[2]/div[1]"));
        gender.click();

        WebElement mobileNumber=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[4]/div[2]/input"));
        mobileNumber.sendKeys("8977977977");

        WebElement date=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[5]/div[2]/div[1]/div/input"));
        date.sendKeys(Keys.CONTROL+"a");
        date.sendKeys("19 Apr 1993");
        date.sendKeys(Keys.RETURN);

        WebElement hobby=driver.findElement(By.xpath("//label[text()='Sports']"));
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", hobby);

        WebElement addres=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[9]/div[2]/textarea"));
        addres.sendKeys("улица Пушкина, дом Колотушкина 9-2");

        WebElement state=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[10]/div[2]/div/div/div[1]"));
        state.click();
        Actions actions=new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ENTER).perform();

        WebElement submit=driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[11]/div/button"));
        submit.click();

        WebElement message=driver.findElement(By.xpath("//html/body/div[4]/div/div/div[1]/div"));
        Assert.assertEquals(message.getText(), "Thanks for submitting the form");

        driver.quit();

    }

    @Test
    public void testFormRequiredFields() throws InterruptedException {
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        //chromeOptions.addArguments("--headless"); // запуск без отображения окна
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver=new ChromeDriver(chromeOptions);
        driver.get("https://demoqa.com/");

        WebElement forms=driver.findElement(By.xpath("//div/div/div[2]/div/div[2]"));
        forms.click();

        WebElement pracriceForms=driver.findElement(By.cssSelector(".element-list.collapse.show"));
        pracriceForms.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/automation-practice-form");

        String titleForm=driver.findElement(By.cssSelector(".text-center")).getText();
        Assert.assertEquals(titleForm, "Practice Form");

        WebElement firstname=driver.findElement(By.id("firstName"));
        firstname.sendKeys("Nata");

        WebElement lastName=driver.findElement(By.id("lastName"));
        lastName.sendKeys("fff");

        driver.findElement(By.xpath("//label[contains(text(), 'Male')]")).click();
        driver.findElement(By.id("userNumber")).sendKeys("1234567890");
        Thread.sleep(1000);

        driver.findElement(By.id("submit")).click();
        Thread.sleep(1000);

        String modalTitle=driver.findElement(By.id("example-modal-sizes-title-lg")).getText();
        Assert.assertEquals("Thanks for submitting the form", modalTitle);

        String firstNameValue=driver.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td")).getText();
        Assert.assertEquals("Nata fff", firstNameValue);

        String phoneValue=driver.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td")).getText();
        Assert.assertEquals("1234567890", phoneValue);
        //Thread.sleep(500); //если хочется посмотреть что получилось в браузере
        driver.quit();

    }

    @Test
    public void testLogin() {
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(driver.findElement(By.className("title")).getText(), "Products");
        driver.quit();
    }

    @Ignore
    public static class YasminJaborovaTest {

        @DataProvider(name = "userData")
        public Object[][] getUserData() {
            List <String[]> users=Arrays.asList(
                    new String[]{"standard_user", "secret_sauce"},
                    new String[]{"problem_user", "secret_sauce"},
                    new String[]{"error_user", "secret_sauce"},
                    new String[]{"visual_user", "secret_sauce"}
            );
            return users.toArray(new Object[users.size()][]);
        }

        @Test(dataProvider = "userData")
        public void testAuthorizationWithMultipleUsers(String username, String password) throws InterruptedException {
            //1.Launch new tab in browser
            WebDriver driver=new ChromeDriver();
            //2.Follow the link
            driver.get("https://www.saucedemo.com/");
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            //3.Fill out all required fields
            WebElement usernameField=driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
            WebElement passwordField=driver.findElement(By.xpath("//*[@id=\"password\"]"));
            WebElement loginButton=driver.findElement(By.xpath("//*[@id=\"login-button\"]"));

            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            Thread.sleep(1000);
            loginButton.click();

            //4.Compare current and expected behaviors
            WebElement headerOfPage=driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
            Assert.assertEquals(headerOfPage.getText(), "Products");

            driver.quit();
        }

    }

    @Ignore
    public static class YasminJaborovaSecondTest {

        @Test
        public void testCartFunctionality() {
            WebDriver driver=new ChromeDriver();
            driver.manage().window().maximize();

            //1.Follow the link
            driver.get("https://magento.softwaretestingboard.com/");

            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));

            //2. Hover over 'Women' dropdown.
            WebElement womenDropdown=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ui-id-4\"]")));
            JavascriptExecutor js=(JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", womenDropdown);
            Actions actions=new Actions(driver);
            actions.moveToElement(womenDropdown).perform();

            //Hover over 'Tops' button
            WebElement topsButton=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ui-id-9\"]/span[2]")));
            actions.moveToElement(topsButton).perform();

            //Select 'Jackets'
            WebElement jacketsButton=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ui-id-11\"]/span")));
            jacketsButton.click();

            //Select 1 item - jacket
            WebElement item=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol/li[3]/div/div/strong/a")));
            item.click();

            //Select size and color of item, and add it to the cart
            WebElement sizeOfItemL=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"option-label-size-143-item-169\"]")));
            WebElement colorOfItem=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"option-label-color-93-item-49\"]")));
            WebElement addToCartButton=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-addtocart-button\"]")));
            js.executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
            sizeOfItemL.click();
            colorOfItem.click();
            addToCartButton.click();

            //Go to Shopping cart
            WebElement shoppingCartButton=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div/a")));
            shoppingCartButton.click();

            //Proceed to Checkout
            WebElement proceedToCheckoutButton=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/ul/li[1]/button")));
            js.executeScript("arguments[0].scrollIntoView(true);", proceedToCheckoutButton);
            proceedToCheckoutButton.click();

            //Compare current and expected results
            WebElement quantityOfItemsInCart=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"opc-sidebar\"]/div[1]/div/div[1]/strong/span[1]")));
            Assert.assertEquals(quantityOfItemsInCart.getText(), "1");

            driver.quit();

        }
    }
    @Test
    public void testDemoQAdoubleClick() {

        WebDriver driver=new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement elementStep=driver.findElement(By.xpath("//div[2]/div/div/div[2]/div/div[1]/div/div[3]/h5"));
        elementStep.click();
        WebElement buttonStep=driver.findElement(By.xpath("//*[@id=\"item-4\"]/span"));
        buttonStep.click();

        WebElement doubleStep=driver.findElement(By.id("doubleClickBtn"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleStep).perform();

        WebElement message=driver.findElement(By.id("doubleClickMessage"));
        Assert.assertEquals(message.getText(), "You have done a double click");

        driver.quit();
    }

    @Test
    public void tesCheckingValidationMessageOnProductPage() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ebay.com/");

        WebElement geoLink = driver.findElement(By.id("gh-eb-Geo-a-default"));
        Actions actions = new Actions(driver);
        actions.moveToElement(geoLink).perform();

        WebElement geoEnLink = driver.findElement(By.id("gh-eb-Geo-a-en"));
        geoEnLink.click();

        WebElement searchInput = driver.findElement(By.id("gh-ac"));
        searchInput.sendKeys("New Casio G-shock GBX-100-2 Digital G-lide Surfers Bluetooth Tide Blue Watch"
                , Keys.ENTER);

        WebElement item = driver.findElement(By.id("item4021337616"));
        item.click();

        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebElement quantityInput = driver.findElement(By.xpath("//input[@name='quantity']"));
        quantityInput.sendKeys(Keys.BACK_SPACE, "-");

        WebElement validationMessage = driver.findElement(By.id("qtyErrMsg"));
        String validationText = validationMessage.getText();
        String colorText =  validationMessage.getCssValue("color");

        Assert.assertEquals(validationText, "Please enter a quantity of 1 or more");
        Assert.assertEquals(colorText, "rgba(213, 11, 11, 1)");

        driver.quit();
    }

    @Test
    public void testAddReviewOnProduct(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationexercise.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement productsButton = driver.findElement(By.xpath("//*[@id='header']//ul/li[2]"));
        productsButton.click();
        WebElement viewProductsButton = driver.findElement(By.xpath("//*//a[@href='/product_details/1']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", viewProductsButton);
        viewProductsButton.click();

        WebElement inputName = driver.findElement(By.xpath("//*[@id='name']"));
        js.executeScript("arguments[0].scrollIntoView(true);", inputName);
        inputName.sendKeys("Specific Name");
        WebElement inputEmail = driver.findElement(By.xpath("//*[@id='email']"));
        inputEmail.sendKeys("yasministesting@domain.com");
        WebElement inputReview = driver.findElement(By.xpath("//*[@id='review']"));
        inputReview.sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Proin tortor purus platea sit eu id nisi litora libero. " +
                "Neque vulputate consequat ac amet augue blandit maximus aliquet congue. " +
                "Pharetra vestibulum posuere ornare faucibus fusce dictumst orci aenean eu " +
                "facilisis ut volutpat commodo senectus purus himenaeos fames primis convallis nisi.");
        WebElement submitButton = driver.findElement(By.xpath("//*[@id='button-review']"));
        submitButton.click();

        WebElement alertSuccessTooltipAboutReview = driver.findElement(By.xpath("//*[@class='alert-success alert']"));
        Assert.assertEquals(alertSuccessTooltipAboutReview.getText(), "Thank you for your review.");

        driver.quit();

    }

    @Test
    public void testVerifySubscriptionInMainPage(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationexercise.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement inputSubscribeEmail = driver.findElement(By.xpath("//*[@id='susbscribe_email']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        inputSubscribeEmail.sendKeys("yasministestingsubs+1@domain.com");
        WebElement subscribeButton = driver.findElement(By.xpath("//*[@class='btn btn-default']"));
        subscribeButton.click();

        WebElement tooltipSuccessSubscription = driver.findElement(By.xpath("//*[@class='alert-success alert']"));
        Assert.assertEquals(tooltipSuccessSubscription.getText(), "You have been successfully subscribed!");

        driver.quit();

    }
}
