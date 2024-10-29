import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NoGroupTest {

    @Test
    public void testSelenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(), "Received!");

        driver.quit();
    }

    @Test
    public void testZara() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.zara.com/us/en/search/home");

        WebElement textBox = driver.findElement(By.xpath("//input[@aria-label='Search text input']"));
        textBox.sendKeys("red");
        Thread.sleep(500);
        textBox.sendKeys(Keys.RETURN);

        Thread.sleep(2000);

        WebElement itemTitle = driver.findElement(
                By.xpath("//*[@id='main']/article/div/div/section/div/section[1]/ul/li[1]/div[2]/div/div/div/div[1]/a/h2"));
        Assert.assertEquals(itemTitle.getText(), "GOLDEN BUTTON KNIT CARDIGAN");

        driver.quit();
    }

    @Test
    public void testLogin() {
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://practicetestautomation.com/practice-test-login/");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("submit"));

        username.sendKeys("student");
        password.sendKeys("Password123");
        login.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("practicetestautomation.com/logged-in-successfully/"));

        driver.quit();
    }
    @Test
    public void testCheckIfElementExists() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://practicetestautomation.com/practice-test-login/");
        WebElement usernameField = driver.findElement(By.id("username"));

        Assert.assertNotNull(usernameField);

        driver.quit();
    }
    @Test
    public void testFindElementByXpath() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://practicetestautomation.com/practice-test-login/");
        WebElement usename = driver.findElement(By.xpath("//*[@type='text']"));

        Assert.assertTrue(usename.isDisplayed());

        driver.quit();
    }

    @Test
    public void testHoverOverButtonTotal() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://coffee-cart.app/");

        Actions actions = new Actions(driver);

        List<WebElement> cups = driver.findElements(
                By.className("cup-body"));
        WebElement anyCup = cups.get(0);
        anyCup.click();

        Thread.sleep(1000);

        WebElement total = driver.findElement(
                By.xpath("//*[@aria-label='Proceed to checkout']"));
        actions.moveToElement(total).perform();

        Thread.sleep(1000);

        WebElement totalPreview = driver.findElement(
                By.className("cart-preview"));

        Assert.assertTrue(totalPreview.isDisplayed());

        driver.quit();

    }

    @Test
    public void testButtonTotalDeleteItem() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://coffee-cart.app/");

        List<WebElement> cups = driver.findElements(
                By.className("cup-body"));
        WebElement anyCup = cups.get(0);
        anyCup.click();

        Thread.sleep(2000);

        WebElement buttonTotal = driver.findElement(
                By.xpath("//*[@aria-label='Proceed to checkout']"));

        Actions action = new Actions(driver);
        action.moveToElement(buttonTotal).perform();

        Thread.sleep(2000);

        WebElement buttonHoverRemove = driver.findElement(
                By.xpath("//*[@aria-label='Remove one Espresso']"));
        buttonHoverRemove.click();

        boolean isPreviewDisplayed = !driver.findElements(
                By.className("cart-preview")).isEmpty();
        Assert.assertFalse(isPreviewDisplayed);

        driver.quit();

    }

    @Test
    public void testButtonTotalAddItem() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://coffee-cart.app/");

        List<WebElement> cups = driver.findElements(
                By.className("cup-body"));
        WebElement anyCup = cups.get(0);
        anyCup.click();

        WebElement buttonTotal = driver.findElement(
                By.xpath("//*[@aria-label='Proceed to checkout']"));

        Actions action = new Actions(driver);
        action.moveToElement(buttonTotal).perform();

        Thread.sleep(2000);

        WebElement buttonHoverAdd = driver.findElement(
                By.xpath("//button[text()='+']"));

        Thread.sleep(2000);

        buttonHoverAdd.click();

        WebElement buttonPlus = driver.findElement(
                By.className("unit-desc"));

        String buttonNameAdd = buttonPlus.getText();

        Assert.assertEquals(buttonNameAdd, "x 2");

        driver.quit();

    }

    @Test
    public void testImdbCheckFilteredRangeForListOfMovies() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com/");
        driver.manage().window().maximize();

        WebElement burgerMenu = driver.findElement(By.id("imdbHeader-navDrawerOpen"));
        burgerMenu.click();

        Thread.sleep(2000);

        WebElement topMoviesButton = driver.findElement(By.xpath("//a[@href='/chart/top/?ref_=nv_mv_250']"));
        topMoviesButton.click();

        WebElement filterButton = driver.findElement(By.xpath("//button[@data-testid='filter-menu-button']"));
        filterButton.click();

        WebElement releaseYearFromField = driver.findElement(By.xpath("//input[@aria-label='Enter release year above']"));
        releaseYearFromField.sendKeys("1970");
        WebElement releaseYearToField = driver.findElement(By.xpath("//input[@aria-label='Enter release year below']"));
        releaseYearToField.sendKeys("1973");
        WebElement closePrompt = driver.findElement(By.xpath("//button[@title='Close Prompt']"));
        closePrompt.click();

        Thread.sleep(2000);

        List<WebElement> resultList = driver.findElements(
                By.xpath("//ul[contains(@class, 'ipc-metadata-list--dividers-between')]//li"));

        boolean allReleaseYearsInRange = true;
        for(WebElement years : resultList) {
            String text = years.getText();
            String[] words = text.split("\\s+");
            for (String word : words) {
                try {
                    int year = Integer.parseInt(word);
                    if ((year < 1970 || year > 1973) && word.length() == 4) {
                        allReleaseYearsInRange = false;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        Assert.assertTrue(allReleaseYearsInRange, "There's a movie in the list with release date out of range");

        driver.quit();
    }

    @Test
    public void testImdbTryToRateMovieWhenUnauthorized() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com./");
        driver.manage().window().maximize();

        WebElement searchField = driver.findElement(By.xpath("//input[@placeholder]"));
        searchField.sendKeys("Хороший, плохой, злой" + Keys.RETURN);

        List<WebElement> searchResult = driver.findElements(
                By.xpath("//ul[contains(@class, 'ipc-metadata-list ipc-metadata-list--dividers-after')]//li"));
        for (WebElement movie : searchResult) {
            String movieInfo = movie.getText();
            if (movieInfo.contains("1966")) {
                movie.click();
                break;
            }
        }

        WebElement yourRatingRateButton = driver.findElement(
                By.xpath("//button[@aria-label='Rate Хороший, плохой, злой']"));
        yourRatingRateButton.click();

        Thread.sleep(1000);

        WebElement tenStars = driver.findElement(By.xpath("//div[@class='ipc-starbar']//div[2]//button[10]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",tenStars);

        WebElement confirmStarsRateButton = driver.findElement(
                By.xpath("//button[contains(@class, 'ipc-rating-prompt__rate-button')]"));
        confirmStarsRateButton.click();

        String signInPageHeader = driver.findElement(By.tagName("h1")).getText();

        Assert.assertEquals(signInPageHeader, "Sign in");

        driver.quit();
    }

    @Test
    public void testLamdbatestRegistrationWithValidCredentials() {
        String password = "myPassword";
        String uniqueEmail = "elevator" + System.currentTimeMillis() + "@test.com";

        WebDriver driver = new ChromeDriver();

        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=common/home");

        WebElement myAccountHover = driver.findElement(
                By.xpath("(//a[@href='https://ecommerce-playground.lambdatest.io/index.php?route=account/account'])[2]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(myAccountHover).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement dropdownRegister = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='https://ecommerce-playground.lambdatest.io/index.php?route=account/register']")));
        dropdownRegister.click();

        driver.findElement(By.name("firstname")).sendKeys("Johnny");
        driver.findElement(By.id("input-lastname")).sendKeys("Elevator");
        driver.findElement(By.id("input-email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("input-telephone")).sendKeys("+15555555577");
        driver.findElement(By.id("input-password")).sendKeys(password);
        driver.findElement(By.id("input-confirm")).sendKeys(password);

        actions.moveToElement(driver.findElement(By.id("input-agree"))).click().perform();
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Your Account Has Been Created!");

        driver.quit();
    }

    @Test
    public void testLamdbatestLoginWithValidCredentials() {
        String password = "myPassword";
        String email = "elevator1test@gmail.com";

        WebDriver driver = new ChromeDriver();

        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=common/home");

        WebElement myAccountHover = driver.findElement(
                By.xpath("(//a[@href='https://ecommerce-playground.lambdatest.io/index.php?route=account/account'])[2]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(myAccountHover).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement dropdownLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='https://ecommerce-playground.lambdatest.io/index.php?route=account/login']")));
        dropdownLogin.click();

        driver.findElement(By.id("input-email")).sendKeys(email);
        driver.findElement(By.id("input-password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Login']")).click();

        Assert.assertEquals(driver.findElement(By.tagName("h2")).getText(), "My Account");

        driver.quit();
    }

    @Test
    public void testSearchWiki() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://en.wikipedia.org/wiki/Main_Page");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.name("search"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.name("search")).sendKeys("Selenium");

        driver.findElement(By.cssSelector("#searchform > div > button")).click();

        WebElement articleTitle = driver.findElement(By.xpath("//*[@id='firstHeading']/span[text()='Selenium']"));
        Assert.assertEquals(articleTitle.getText(), ("Selenium"));
        driver.quit();
    }

    @Test
    public void testTicketSearchPage() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://airmontenegro.com/en");

        WebElement agreeButton = driver.findElement(By.xpath("//*[@id=\"content_wrap\"]/div[2]/button"));
        agreeButton.click();
        WebElement searchFilter = driver.findElement(By.xpath("//*[@id=\"reservations_table\"]"));

        Assert.assertTrue(searchFilter.isDisplayed());

        driver.quit();
    }

    @Test
    public void testRatatype() {
        WebDriver driver;
        driver = new ChromeDriver(new ChromeOptions().addArguments("incognito", "start-maximized"));

        driver.get("https://www.ratatype.com/");

        driver.findElement(By.xpath("//*[text()='Test your speed']")).click();
        WebElement title = driver.findElement(By.xpath("//*[@class='h2']"));

        Assert.assertEquals(title.getText(), "Typing Certification Test");

        driver.quit();
    }

    @Test
    public void testRatatypeSelectSpanishLanguage() throws InterruptedException {
        WebDriver driver;
        driver = new ChromeDriver(new ChromeOptions().addArguments("incognito", "start-maximized"));

        driver.get("https://www.ratatype.com/");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@data-bs-target='#footerLangModal']")).click();
        WebElement language = driver.findElement(By.xpath("//a[contains(@href,'/es/')]"));
        Thread.sleep(2000);
        language.click();

        WebElement title = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(title.getText(), "Prácticas de escritura a mecanografía");

        driver.quit();
    }

    // !!! !!! !!!
    // В тесте используется Robot для имитации нажатия клавиш на клавиатуре.
    // Если закрыть окно с тестом до окончания набора текста, то Robot продолжит нажимать клавиши
    // в ранее открытом окне (в IDE) пока не закончится текст или пока не будет остановлен тест.
    // !!! !!! !!!
    @Test
    public void testRatatypePrintSpeed() throws AWTException {
        WebDriver driver;
        driver = new ChromeDriver(new ChromeOptions().addArguments("incognito", "start-maximized"));

        driver.get("https://www.ratatype.com/typing-tutor/");

        Robot robot = new Robot();
        robot.delay(3000);      // Create a three seconds delay.

        // Фраза "type me to find out how many words per minute you can type", которую нужно напечатать,
        // закодирована числовыми кодами клавиш для раскладки клавиатуры QWERTY
        // ('t'=84; 'y'=89; ' '=32 и т.д.)
        Integer[] intArray = {
                84,89,80,69,32, 77,69,32, 84,79,32, 70,73,78,68,32, 79,85,84,32, 72,79,87,32, 77,65,78,89,32,
                87,79,82,68,83,32, 80,69,82,32, 77,73,78,85,84,69,32, 89,79,85,32, 67,65,78,32, 84,89,80,69
        };

        // Generating key press event for writing the QWERTY letters
        for(int i = 0; i < intArray.length; i++) {
            robot.delay(120);
            robot.keyPress(intArray[i]);
        }

        WebElement titleH2 = driver.findElement(By.xpath("//h2"));
        Assert.assertTrue(titleH2.getText().contains("words per minute"));

        driver.quit();
    }
    @Test
    public void testButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/test/radio.html");

        WebElement option1 = driver.findElement(By.xpath("//input[@value='Option 1']"));
        option1.click();
        Assert.assertTrue(option1.isSelected());

        WebElement option2 = driver.findElement(By.xpath("//input[@value='Option 2']"));
        option2.click();
        Assert.assertTrue(option2.isSelected());

        WebElement option3 = driver.findElement(By.xpath("//input[@value='Option 3']"));
        option3.click();
        Assert.assertTrue(option3.isSelected());

        WebElement checkbox1 = driver.findElement(By.xpath("//input[@value='checkbox1']"));
        checkbox1.click();
        Assert.assertTrue(checkbox1.isSelected());

        WebElement checkbox2 = driver.findElement(By.xpath("//input[@value='checkbox2']"));
        checkbox2.click();
        Assert.assertTrue(checkbox2.isSelected());

        WebElement checkbox3 = driver.findElement(By.xpath("//input[@value='checkbox3']"));
        checkbox3.click();
        Assert.assertTrue(checkbox3.isSelected());

        driver.quit();
    }

    @Test
    public void testLoginPage() {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://demo.guru99.com/test/login.html");

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("abcd@gmail.com");

        WebElement passwordField = driver.findElement(By.name("passwd"));
        passwordField.sendKeys("abcdefghlkjl");

        WebElement loginButton = driver.findElement(By.id("SubmitLogin"));
        loginButton.click();

        String expectedUrl = "https://demo.guru99.com/test/success.html";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);

        driver.quit();
    }

    @Test
    public void loginTest() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");

        WebElement userNameBox = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        userNameBox.sendKeys("tomsmith");

        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordBox.sendKeys("SuperSecretPassword!");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login\"]/button"));
        loginButton.click();

        WebElement message = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h4"));
        Assert.assertEquals(message.getText(), "Welcome to the Secure Area. When you are done click logout below.");

        driver.quit();
    }


    @Test
    public void testAramH() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bestbuy.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.id("gh-search-input")).sendKeys("laptop");
        driver.findElement(By.xpath("//span[@class='header-search-icon']")).click();

        WebElement message = driver.findElement(By.xpath("//div[@class='title-wrapper title']/span[2]/span[2]"));

        Assert.assertEquals(message.getText(), "in Laptops");

        driver.quit();

    }

    @Test
    public void orderItemTest() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement userNameBox = driver.findElement(By.xpath("//*[@id='user-name']"));
        userNameBox.sendKeys("standard_user");

        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();

        WebElement itemFleeceJacketAddToCart = driver.findElement(
                By.xpath("//*[@id=\"add-to-cart-sauce-labs-fleece-jacket\"]"));
        itemFleeceJacketAddToCart.click();

        WebElement itemOnesie = driver.findElement(
                By.xpath("//*[@id=\"add-to-cart-sauce-labs-onesie\"]"));
        itemOnesie.click();

        WebElement cartIcon = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
        cartIcon.click();

        WebElement checkoutButton = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        checkoutButton.click();

        WebElement firstNameField = driver.findElement(By.xpath("//*[@id=\"first-name\"]"));
        firstNameField.sendKeys("Allie");

        WebElement lastNameField = driver.findElement(By.xpath("//*[@id=\"last-name\"]"));
        lastNameField.sendKeys("Smith");

        WebElement zipCodeField = driver.findElement(By.xpath("//*[@id=\"postal-code\"]"));
        zipCodeField.sendKeys("10001");

        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"continue\"]"));
        continueButton.click();

        WebElement total = driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]"));
        Assert.assertEquals(total.getText(), "Total: $62.62");

        WebElement finishButton = driver.findElement(By.xpath("//*[@id=\"finish\"]"));
        finishButton.click();

        WebElement completeOrder = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2"));
        Assert.assertEquals(completeOrder.getText(), "Thank you for your order!");

        driver.quit();
    }
    @Test
    public void totalSumTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement userNameBox = driver.findElement(By.xpath("//*[@id='user-name']"));
        userNameBox.sendKeys("standard_user");

        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();

        WebElement itemFleeceJacketAddToCart = driver.findElement(
                By.xpath("//*[@id=\"add-to-cart-sauce-labs-fleece-jacket\"]"));
        itemFleeceJacketAddToCart.click();

        WebElement itemOnesie = driver.findElement(
                By.xpath("//*[@id=\"add-to-cart-sauce-labs-onesie\"]"));
        itemOnesie.click();

        WebElement cartIcon = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
        cartIcon.click();

        WebElement checkoutButton = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        checkoutButton.click();

        WebElement firstNameField = driver.findElement(By.xpath("//*[@id=\"first-name\"]"));
        firstNameField.sendKeys("Allie");

        WebElement lastNameField = driver.findElement(By.xpath("//*[@id=\"last-name\"]"));
        lastNameField.sendKeys("Smith");

        WebElement zipCodeField = driver.findElement(By.xpath("//*[@id=\"postal-code\"]"));
        zipCodeField.sendKeys("10001");

        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"continue\"]"));
        continueButton.click();

        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        double calculatedTotal = 0.0;

        for (WebElement priceElement : prices) {
            String priceText = priceElement.getText();
            double price = Double.parseDouble(priceText.replace("$", "").trim());
            calculatedTotal += price;
        }
        WebElement total = driver.findElement(
                By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[6]"));
        String totalText = total.getText();
        double displayedTotal = Double.parseDouble(totalText.replace("Item total: $", ""));

        Assert.assertEquals(calculatedTotal, displayedTotal, 0.01);

        WebElement finishButton = driver.findElement(By.xpath("//*[@id=\"finish\"]"));
        finishButton.click();

        WebElement completeOrder = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2"));
        Assert.assertEquals(completeOrder.getText(), "Thank you for your order!");

        driver.quit();
    }

    @Test
    public void testReebokChooseProductOfSpecificColor() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.reebok.com/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement womenMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='WOMEN']")));

        Actions actions = new Actions(driver);
        actions.moveToElement(womenMenu).perform();

        WebElement hatsCategory = driver.findElement(By.xpath("//a[text()='Hats']"));
        hatsCategory.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));

        actions.scrollToElement(driver.findElement(By.xpath("//span[text()='Activity']/../../..")));
        WebElement colorFilter = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='Color']/../../..")));
        colorFilter.click();

        WebElement colorButton = driver.findElement(By.xpath("//input[@value='Color:\"purple\"']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", colorButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Applied Filters']")));
        WebElement selectedItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[@href='/p/100232204/reebok-logo-cuff-hat'])[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectedItem);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add to Cart']")));
        WebElement selectedItemColor = driver.findElement(By.id("productInfoPanel"));

        Assert.assertTrue(selectedItemColor.getText().contains("Midnight Plum"));

        driver.quit();
    }

    @Test
    public void trackingNumberSearch () throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.mondospedizioni.com");

        Thread.sleep(200);

        WebElement cookiesAccept = driver.findElement(By.xpath("//*[@id='iubenda-cs-banner']/div/div/div/div[3]/div[2]/button"));
        cookiesAccept.click();

        WebElement trackingSpedizioni = driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul[1]/li[7]/a"));
        trackingSpedizioni.click();

        Thread.sleep(500);

        WebElement searchBox = driver.findElement(By.xpath("//*[@id='tracking_code']"));
        searchBox.sendKeys("wehjdfhjg346754");

        WebElement searchButton = driver.findElement(By.xpath("//*[@id='send_trk']"));
        searchButton.click();

        Thread.sleep(1500);

        WebElement message = driver.findElement(By.xpath("//*[@id='ja_172995591326639878']/div/div[3]"));
        Assert.assertEquals(message.getText(), "Il tracking inserito non è stato trovato nel nostro database.\n" +
                "Per assistenza contattare il servizio clienti.");

        driver.quit();
    }

    @Test
    public void testProducts() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://openweather.co.uk/");
        WebElement productButton = driver.findElement(By.xpath("//*[@id='desktop-menu']/ul/li[1]/a"));
        productButton.click();
        Assert.assertEquals(driver.getTitle(), "Products - OpenWeather");
        System.out.println(driver.getWindowHandles());
        driver.quit();
    }

    @Test
    public void testDashboard() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://openweather.co.uk/");
        WebElement dashboardButton = driver.findElement(By.xpath("//*[@id='desktop-menu']/ul/li[2]/a"));
        dashboardButton.click();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertEquals(driver.getCurrentUrl(), "https://dashboard.openweather.co.uk/");
        driver.quit();
    }

    @Test
    public void testHowToBuyButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://openweather.co.uk/");
        WebElement dashboardButton = driver.findElement(By.xpath("//*[@id='desktop-menu']/ul/li[3]/a"));
        dashboardButton.click();
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2500)");
        WebElement howToBuyButton = driver.findElement(By.xpath("//*[@id='current']/div/div/div/a[1]"));
        js.executeScript("arguments[0].click();", howToBuyButton);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertEquals(driver.getCurrentUrl(), "https://openweather.co.uk/how-to-buy");
        driver.quit();
    }

    @Test
    public void emptyFieldsTest() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement userNameBox = driver.findElement(By.xpath("//*[@id='user-name']"));
        userNameBox.sendKeys("standard_user");

        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();

        WebElement itemOnesie = driver.findElement(
                By.xpath("//*[@id=\"add-to-cart-sauce-labs-onesie\"]"));
        itemOnesie.click();

        WebElement cartIcon = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
        cartIcon.click();

        WebElement checkoutButton = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        checkoutButton.click();

        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"continue\"]"));
        continueButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3"));
        Assert.assertEquals(errorMessage.getText(), "Error: First Name is required");

        driver.quit();
    }

    @Test
    public void testBrandFilter() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://fh.by/");
        driver.manage().window().maximize();

        WebElement christmasDecorationsSection = driver.findElement(
                By.xpath("//a[@href='/interer/category/elochnye-igrushki-podveski']"));
        christmasDecorationsSection.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[text()='Ёлочные игрушки, украшения на ёлку']")));

        Thread.sleep(2000);

        WebElement scrollToSeeButton = driver.findElement(By.xpath("//div[text()='Категории']"));
        Actions actions = new Actions(driver);
        actions.scrollToElement(scrollToSeeButton);

        WebElement viewAllButton = driver.findElement(By.xpath("(//button[text()='Посмотреть все'])[1]"));
        viewAllButton.click();

        WebElement brandSearchField = driver.findElement(By.id("brand-search-input"));
        String brandForSearch = "villeroy";
        brandSearchField.sendKeys(brandForSearch);

        WebElement firstCheckboxUnderBrandSearchField = driver.findElement(
                By.xpath("//input[@id='brand-search-input']/../../following-sibling::div//label"));

        String filteredCheckbox = firstCheckboxUnderBrandSearchField.getText().toLowerCase();

        Assert.assertTrue(filteredCheckbox.contains(brandForSearch));

        driver.quit();
    }

    @Test
    public void testAddToCart() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://fh.by/");
        driver.manage().window().maximize();

        WebElement interiorSection = driver.findElement(By.xpath("//a[text()='Интерьер']"));
        interiorSection.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement itemToBuy = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(@class, 'ProductCard_isCatalog')])[1]")));
        itemToBuy.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Описание']")));

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@type='submit'])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);

        WebElement cartIcon = driver.findElement(By.xpath("//a[@aria-label='Корзина']"));
        cartIcon.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Корзина']")));

        Assert.assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isDisplayed());

        driver.quit();
    }
    //EdGatin
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
