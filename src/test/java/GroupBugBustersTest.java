import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class GroupBugBustersTest{

    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeMethod // Den
    public void startUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--start-maximized");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test //Den
    public void testSearchCheeseInVkusville(){
        driver.get("https://vkusvill.ru/");

        WebElement search = driver.findElement(By.cssSelector(".HeaderSearchBlock__Input.js-vv21-search__search-input.js-search-autotyping"));
        search.sendKeys("Сыр");

        WebElement searchButton = driver.findElement(By.cssSelector(".HeaderSearchBlock__BtnSearchImg.js-vv21-seacrh__icon-btn"));
        searchButton.click();

        WebElement title = driver.findElement(By.cssSelector(".VV21_SearchPage__Title.h4_desktop.h4_tablet.js-search-total-num-container"));
        Assert.assertEquals(title.getText().substring(title.getText().indexOf("Сыр")), "Сыр»");
    }


    @Test //Den
    public void test() {
        driver.get("https://tasnet.uz"); // - Открыть страницу
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Tasnet - Wi-Fi authorization via SMS and social networks Hotspot", "Tittle doesn't match");
    }
    @Test //Den
    public void test2(){
        driver.get("https://tasnet.uz"); // - Открыть страницу
        WebElement cost = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[1]/ul/li[3]/a"));
        cost.click();

        // regex для проверки ссылки сайта. Включает в себя https://tasnet.uz/pricing и https://tasnet.uz/en/pricing
        Assert.assertTrue(driver.getCurrentUrl().matches("^https://tasnet.uz(/en)?/pricing"), "Wrong page");
    }

    @Test //Den
    public void test3() {
        driver.get("https://tasnet.uz/business");
        WebElement searchEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        searchEmail.sendKeys("Java", Keys.ENTER);
    }


    @Test //Ivan
    public void CookiesTest(){
        driver.get("https://monkeytype.com");
        Assert.assertTrue(clickElementAndVerifyDisappearance(By.cssSelector(".acceptAll")));
    }
    @Test //Ivan
    public void ThirdPartyCookiesTest(){
        driver.get("https://monkeytype.com");
        AcceptCookies();
        Assert.assertTrue(clickElementAndVerifyDisappearance(By.cssSelector(".fc-button.fc-cta-consent.fc-primary-button")));
    }
    @Test
    public void typingWithDefaultSettingsTest(){
        driver.get("https://monkeytype.com");
        AcceptAll();
        startTyping();
        Assert.assertTrue(isResultDisplayed());
    }
    @Test //Ivan
    public void modeWordsTest(){
        driver.get("https://monkeytype.com");
        AcceptAll();
        selectMode("words");
        WebElement nextTestButton = driver.findElement(By.cssSelector("#nextTestButton"));
        for (int i = 1; i <= 5; i++) {
            selectOptionModeWords(i);
            startTyping();
            Assert.assertTrue(isResultDisplayed());
            sleep(5000);
            disableAdd();
            nextTestButton.click();
            sleep(500);
        }
    }
    @AfterMethod //Den
    public void shutDown() {
        driver.quit();
    }
  
    public boolean isPresent(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    public void AcceptAll(){ //Ivan
        AcceptCookies();
        AcceptThirdPartyCookies();
        sleep(500);
    }
    private void AcceptCookies(){ //Ivan
        WebElement acceptAllButton = driver.findElement(By.className("acceptAll"));
        acceptAllButton.click();
    }
    private void AcceptThirdPartyCookies(){ //Ivan
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement consentButton = driver.findElement(By.cssSelector(".fc-button.fc-cta-consent.fc-primary-button"));
        consentButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    private boolean clickElementAndVerifyDisappearance(By locator){ //Ivan
        driver.findElement(locator).click();
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    private void startTyping(){ //Ivan
        WebElement input = driver.findElement(By.id("wordsInput"));
        sleep(2000);
        List<WebElement> words;
        String activeWord;
        try {
            while (isPresent(By.cssSelector(".word"))) {
                words = driver.findElements(By.cssSelector(".word:not(.typed)"));
                for (WebElement word : words) {
                    activeWord = word.getText();
                    for (char c : activeWord.toCharArray()) {
                        input.sendKeys(Character.toString(c));
                        sleep(getRandomNormalInt(60, 30));
                    }
                    input.sendKeys(" ");
                }
            }
        }
        catch (ElementNotInteractableException ignored) {}
        sleep(500);
    }
    private void selectMode(String option){ //Ivan
        By locator;
        switch (option){
            case "time": locator = By.xpath("//button[@mode = 'time']"); break;
            case "words": locator = By.xpath("//button[@mode = 'words']"); break;
            case "quote": locator = By.xpath("//button[@mode = 'quote']"); break;
            case "zen": locator = By.xpath("//button[@mode = 'zen']"); break;
            case "custom":
            default: locator = By.xpath("//button[@mode = 'custom']");
        }
        driver.findElement(locator).click();
        sleep(500);
    }
    private void selectOptionModeWords(int optionNumber){ //Ivan
        //Check if window selected
        WebElement optionWordsButton = driver.findElement(By.xpath("//button[@mode = 'words']"));
        if (!optionWordsButton.getAttribute("class").contains("active")) throw new SkipException("Mode words hasn't been selected");
        By locator = switch (optionNumber) {
            case 1 -> By.xpath("//button[@wordcount='10']");
            case 2 -> By.xpath("//button[@wordcount='25']");
            case 3 -> By.xpath("//button[@wordcount='50']");
            case 4 -> By.xpath("//button[@wordcount='100']");
            default -> By.xpath("//button[@wordcount='custom']");
        };
        WebElement optionButton = driver.findElement(locator);
        if (!optionButton.getAttribute("wordcount").equals("custom")){
            optionButton.click();
        } else{
            int wordsDisplayed = 200;
            optionButton.click();
            driver.findElement(By.xpath("//form/input[@type='number']")).sendKeys(String.valueOf(wordsDisplayed));
            driver.findElement(By.xpath("//form/button")).click();
        }
        sleep(500);
    }
    private void disableAdd(){ //Ivan
        By locator = By.cssSelector("#ad_position_box");
        if(!isPresent(locator)) return;
        WebElement Add = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].remove();", Add);
        sleep(10000);
    }
    private boolean isResultDisplayed(){
        WebElement resultContainer = driver.findElement(By.cssSelector("#result"));
        return !resultContainer.getAttribute("class").contains("hidden");
    }

    private void sleep(double milis)  { //Ivan
        try {
            Thread.sleep((long) milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static int getRandomNormalInt(int mean, int stdDev) { //Ivan
        Random random = new Random();
        double gaussian = random.nextGaussian(); // Generates a number from a standard normal distribution
        double value = mean + gaussian * stdDev; // Scale it to the desired mean and standard deviation
        return (int) Math.abs(Math.round(value)); // Round to the nearest integer
    }
  
  
    @Test
    public void loginTest() {
// Олег Шулаев тест
        driver.get("https://the-internet.herokuapp.com/login");

        WebElement userNameBox = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        userNameBox.sendKeys("tomsmith");

        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordBox.sendKeys("SuperSecretPassword!");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login\"]/button"));
        loginButton.click();

        WebElement message = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h4"));
        Assert.assertEquals(message.getText(), "Welcome to the Secure Area. When you are done click logout below.");

        // проверка пуш
    }
}
