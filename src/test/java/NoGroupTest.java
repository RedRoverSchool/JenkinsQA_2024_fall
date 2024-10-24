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
    public void AramHtest() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bestbuy.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.id("gh-search-input")).sendKeys("laptop");
        driver.findElement(By.xpath("//span[@class='header-search-icon']")).click();

        WebElement message = driver.findElement(By.xpath("//div[@class='title-wrapper title']/span[2]/span[2]"));

        Assert.assertEquals(message.getText(), "in Laptops");

        driver.quit();

    }
}
