import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GroupLeadsAndRoversTest {

    private WebDriver driver;

    private void startStrategTests() throws InterruptedException {
        driver.get("https://strateg.by");
        Thread.sleep(500);
    }

    private void openInteractionsPage(){
        WebElement sectionInteractions = driver.findElement(By.xpath("//*[@class = 'card mt-4 top-card']//h5[contains(text(), 'Interactions')]"));
        sectionInteractions.click();
    }

    private void switchToGridVisualisation(){
        WebElement buttonGridFormat = driver.findElement(By.xpath("//a[@ id = 'demo-tab-grid']"));
        buttonGridFormat.click();
    }

    private void openBookStoreApplication() {
        WebElement buttonBookStoreApplication = driver.findElement(By.xpath("//*[@class = 'card mt-4 top-card']//h5[contains(text(), 'Book Store Application')]"));
        scroolDown();
        buttonBookStoreApplication.click();
    }

    private void scroolDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    private void startTestDemoQaCom () {
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");
    }

    @BeforeMethod
    private void initDriver() throws InterruptedException {
        this.driver = new ChromeDriver();
    }

    @AfterMethod
    private void quitDriver() {
        driver.quit();
    }

    @Test
    public void testSearchBoxIsPresent() throws InterruptedException {

        driver.get("https://www.google.com");
        Thread.sleep(1000);

        WebElement searchBox = driver.findElement(By.name("q"));
        Assert.assertTrue(searchBox.isDisplayed(), "Search box should be displayed");
    }

    @Test
    public void testGoogleLogoIsDisplayed() throws InterruptedException {

        driver.get("https://www.google.com");
        Thread.sleep(1000);

        WebElement googleLogo = driver.findElement(By.xpath("//img[@alt='Google']"));
        Assert.assertTrue(googleLogo.isDisplayed(), "Google logo should be displayed");
    }

    @Test
    public void testFormAuthentication() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://the-internet.herokuapp.com/");

        WebElement formAuthentication = driver.findElement(By.xpath("//a[@href='/login']"));
        formAuthentication.click();

        WebElement loginTitle = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h2"));
        Assert.assertEquals(loginTitle.getText(), "Login Page");
    }

    @Test
    public void testCorectPassword() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://the-internet.herokuapp.com/login");

        WebElement userName = driver.findElement(By.id("username"));
        WebElement userPassword = driver.findElement(By.id("password"));
        WebElement buttonLogin = driver.findElement(By.cssSelector("button[type='submit']"));

        userName.sendKeys("tomsmith");
        userPassword.sendKeys("SuperSecretPassword!");
        buttonLogin.click();

        String expectedUrl = "https://the-internet.herokuapp.com/secure";
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testStrategArendaPomewenii() throws InterruptedException {

        driver.manage().window().maximize();
        startStrategTests();

        WebElement submitArendaPomewenii = driver.findElement(By.xpath("//*[@class= 't770__bottomwrapper t-align_center']//a[contains(text(), 'Аренда помещений')]"));
        submitArendaPomewenii.click();
        Thread.sleep(1000);

        WebElement mfkStrateg01 = driver.findElement(By.xpath("//*[@data-tab-rec-ids='793111509']"));
        Assert.assertEquals(mfkStrateg01.getText(), "МФК СТРАТЕГ 01\n" +
                "ул.Янки Лучины 5");
    }

    @Test
    public void testStrategObekti() throws InterruptedException {

        startStrategTests();

        WebElement submitMenuButton = driver.findElement(By.xpath("//button[@aria-label='Навигационное меню']"));
        submitMenuButton.click();
        Thread.sleep(200);

        WebElement submitObektiButton = driver.findElement(By.xpath
                ("//*[@class= 't770 t770__positionabsolute tmenu-mobile__menucontent_hidden']//nav[@class='t770__listwrapper t770__mobilelist']//a[contains(text(), 'Объекты')]"));
        submitObektiButton.click();
        Thread.sleep(200);

        WebElement mfkStrateg01 = driver.findElement(By.xpath("//*[@id='cardtitle1_793111500']"));
        Assert.assertEquals(mfkStrateg01.getText(),
                "МНОГОФУНКЦИОНАЛЬНЫЙ\nКУЛЬТУРНО-РАЗВЛЕКАТЕЛЬНЫЙ\nКОМПЛЕКС СТРАТЕГ 01\nг.Минск, ул. Янки Лучины , дома 5 и 7");
    }

    @Test
    public void testStrategLocation() throws InterruptedException {

        driver.manage().window().maximize();
        startStrategTests();

        Assert.assertEquals(driver.getTitle(), "Коммерческая недвижимость в Минске – Аренда офисов и складов | Стратег");
        WebElement buttonLocation = driver.findElement(By.xpath("//*[@class = 't770__bottomwrapper t-align_center']//li[@class='t770__list_item']//a[contains(text(), 'Расположение')]"));
        buttonLocation.click();
        Thread.sleep(1000);

        WebElement ymaps = driver.findElement(By.xpath("//ymaps[@class = 'ymaps-2-1-79-balloon__content']//ymaps//ymaps"));
        Assert.assertEquals(ymaps.getText(),
                "МФК СТРАТЕГ-01\nг.Минск, ул. Янки Лучины, 5\nг.Минск, ул. Янки Лучины, 7"
        );
    }

    @Test
    public void testContactMethods() throws InterruptedException {

        driver.manage().window().maximize();
        startStrategTests();

        Assert.assertEquals(driver.getTitle(), "Коммерческая недвижимость в Минске – Аренда офисов и складов | Стратег");
        WebElement chatButton = driver.findElement(By.xpath("//div[@class='t898__btn']"));
        chatButton.click();

        WebElement phoneButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-phone_wrapper t898__icon_link']"));
        Assert.assertTrue(phoneButton.isDisplayed());

        WebElement viberButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-viber_wrapper t898__icon_link']"));
        Assert.assertTrue(viberButton.isDisplayed());

        WebElement telegramButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-telegram_wrapper t898__icon_link']"));
        Assert.assertTrue(telegramButton.isDisplayed());

        WebElement whatsAppButton = driver.findElement(By.xpath("//div[@class = 't898__btn']//a[@class = 't898__icon t898__icon-whatsapp_wrapper t898__icon_link']"));
        Assert.assertTrue(whatsAppButton.isDisplayed());
    }

    @Test
    public void testNumberOfCategories() {

        startTestDemoQaCom();

        List<WebElement> categoriestPanel = driver.findElements(By.xpath("//*[@class = 'card mt-4 top-card']"));
        Assert.assertEquals(categoriestPanel.size(), 6 );
    }

    @Test
    public void testSelectableGridSelectingObjects() throws InterruptedException {

        startTestDemoQaCom();
        openInteractionsPage();

        WebElement sectionSelectable = driver.findElement(By.xpath("//li[@class = 'btn btn-light ']//span[text() = 'Selectable']"));
        sectionSelectable.click();
        switchToGridVisualisation();

        List<WebElement> gridPanel = driver.findElements(By.xpath("//div[@ id = 'gridContainer']//li"));
        Assert.assertEquals(gridPanel.size(), 9);
        scroolDown();
        gridPanel.get(0).click();
        gridPanel.get(2).click();
        gridPanel.get(4).click();
        gridPanel.get(6).click();
        gridPanel.get(8).click();
        Thread.sleep(1000);

        List<WebElement> gridActiveIcon = driver.findElements(By.xpath("//div[@ id = 'gridContainer']//li[contains(@class, 'active')]"));
        Assert.assertEquals(gridActiveIcon.size(), 5);
        List<WebElement> gridNotActiveIcon = driver.findElements(By.xpath("//div[@ id = 'gridContainer']//li[contains(@class, 'list-group-item list-group-item-action')]"));
        Assert.assertEquals(gridNotActiveIcon.size(), 4);
    }

    @Test
    public void testSortableGridDragAndDropActions() throws InterruptedException {

        startTestDemoQaCom();
        openInteractionsPage();

        WebElement sectionSelectable = driver.findElement(By.xpath("//li[@class = 'btn btn-light ']//span[text() = 'Sortable']"));
        sectionSelectable.click();
        switchToGridVisualisation();
        Thread.sleep(1000);

        List<WebElement> gridPanel = driver.findElements(By.xpath("//div[@class = 'create-grid']//div"));
        Assert.assertEquals(gridPanel.size(), 9);
        gridPanel.get(0).click();
        Actions action = new Actions(driver);
        action.dragAndDrop(gridPanel.get(0), gridPanel.get(2)).build().perform();
        Thread.sleep(500);

        List<WebElement> newGridPanel = driver.findElements(By.xpath("//div[@class = 'create-grid']//div"));
        Assert.assertEquals(newGridPanel.size(), 9);
        Assert.assertEquals(newGridPanel.get(0).getText(), "Two");
        Assert.assertEquals(newGridPanel.get(1).getText(), "Three");
        Assert.assertEquals(newGridPanel.get(2).getText(), "One");
    }

    @Test
    public void testBookStoreFiltration() throws InterruptedException {

        startTestDemoQaCom();
        openBookStoreApplication();
        Thread.sleep(500);

        List<WebElement> listOfBooks = driver.findElements(By.xpath("//*[@class = 'rt-tbody']//div[@class='rt-tr-group']"));
        Assert.assertEquals(listOfBooks.size(), 10);
        listOfBooks.removeIf(nextBook -> nextBook.getText().equals("    "));
        Assert.assertEquals(listOfBooks.size(), 8);
    }

    @Test
    public void testBookStoreFiltration5BooksPerPage() throws InterruptedException {

        startTestDemoQaCom();
        openBookStoreApplication();
        Thread.sleep(1000);

        scroolDown();
        WebElement fieldRowsPerPage = driver.findElement(By.xpath("//*[@aria-label='rows per page']"));
        fieldRowsPerPage.click();
        WebElement rowsPerPage5 = driver.findElement(By.xpath("//*[@aria-label='rows per page']//option[@value='5']"));
        rowsPerPage5.click();
        WebElement nextButton = driver.findElement(By.xpath("//button[@class='-btn' and ./text()='Next']"));
        nextButton.click();

        List<WebElement> listOfBooks = driver.findElements(By.xpath("//*[@class = 'rt-tbody']//div[@class='rt-tr-group']"));
        Assert.assertEquals(listOfBooks.size(), 5);
        listOfBooks.removeIf(nextBook -> nextBook.getText().equals("    "));
        Assert.assertEquals(listOfBooks.size(), 3);
        WebElement totalPage = driver.findElement(By.xpath("//*[@class='-totalPages']"));
        Assert.assertEquals(totalPage.getText(), "2");
    }

    @Test
    public void testBookStoreSearch() throws InterruptedException {

        startTestDemoQaCom();
        openBookStoreApplication();
        Thread.sleep(500);

        WebElement serchBox = driver.findElement(By.xpath("//*[@class='form-control']"));
        serchBox.sendKeys("O'Reilly Media");
        Thread.sleep(150);

        List<WebElement> listOfBooks = driver.findElements(By.xpath("//*[@class = 'rt-tbody']//div[@class='rt-tr-group']"));
        Assert.assertEquals(listOfBooks.size(), 10);
        listOfBooks.removeIf(nextBook -> nextBook.getText().equals("    "));
        Assert.assertEquals(listOfBooks.size(), 6);
    }
}