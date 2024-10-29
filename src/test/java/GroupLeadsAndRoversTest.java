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
import java.util.Random;

import static java.lang.Thread.sleep;

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
        scrollDown();
        buttonBookStoreApplication.click();
    }

    private void scrollDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    private void startTestDemoQaCom () {
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");
    }
    private String generateUniqueEmail() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Генерируем случайное число от 0 до 9999
        return "JohnDoe" + randomNumber + "@mi.com";
    }

    @BeforeMethod
    private void initDriver() {
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
    public void testCorrectPassword() {

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
        scrollDown();
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

        scrollDown();
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

    @Test
    public void testErrorTextAfterSendMessage() throws InterruptedException {
        driver.get("https://anixtd.ru/contacts.php");
        // Заполнение формы перед каждым тестом
        WebElement userName = driver.findElement(By.name("user_name"));
        userName.sendKeys("Test testov");

        WebElement eMail = driver.findElement(By.name("user_email"));
        eMail.sendKeys("sdfvjkhb@jhbvsd.ru");

        WebElement message = driver.findElement(By.name("MESSAGE"));
        message.sendKeys("проверка связи");

        WebElement submitButton = driver.findElement(By.cssSelector(".faq_sub"));
        submitButton.click();
        Thread.sleep(500);

        WebElement errorMessage = driver.findElement(By.cssSelector(".errortext"));

        Assert.assertEquals(errorMessage.getText(), "Вы не прошли проверку подтверждения личности");
    }

    @Test
    public void testErrorTextColor() throws InterruptedException {
        driver.get("https://anixtd.ru/contacts.php");
        // Заполнение формы перед каждым тестом
        WebElement userName = driver.findElement(By.name("user_name"));
        userName.sendKeys("Test testov");

        WebElement eMail = driver.findElement(By.name("user_email"));
        eMail.sendKeys("sdfvjkhb@jhbvsd.ru");

        WebElement message = driver.findElement(By.name("MESSAGE"));
        message.sendKeys("проверка связи");

        WebElement submitButton = driver.findElement(By.cssSelector(".faq_sub"));
        submitButton.click();
        Thread.sleep(500);

        WebElement errorMessage = driver.findElement(By.cssSelector(".errortext"));
        String color = errorMessage.getCssValue("color");
        String expectedColor = "rgba(255, 0, 0, 1)";

        Assert.assertEquals(color, expectedColor);

    }

    @Test
    public void TestContactList() throws InterruptedException {
        driver.get("https://thinking-tester-contact-list.herokuapp.com");

        WebElement signUpButton = driver.findElement(By.xpath("//button[@id='signup']"));
        signUpButton.click();
        Thread.sleep(1000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInput.sendKeys("Ubergez");

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Odinson");

        WebElement eMail = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmail = generateUniqueEmail();
        eMail.sendKeys(uniqueEmail);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("1234567");

        WebElement submit = driver.findElement(By.xpath("//*[@id='submit']"));
        submit.click();
        sleep(1000);

        WebElement contactList = driver.findElement(By.xpath("//h1[text()='Contact List']"));
        Assert.assertEquals(contactList.getText(),"Contact List");
    }

    @Test
    public  void TestAddContactTitle() throws InterruptedException {
        driver.get("https://thinking-tester-contact-list.herokuapp.com");

        WebElement signUpButton = driver.findElement(By.xpath("//button[@id='signup']"));
        signUpButton.click();
        Thread.sleep(1000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInput.sendKeys("Ubergez");

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Odinson");

        WebElement eMail = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmail = generateUniqueEmail();
        eMail.sendKeys(uniqueEmail);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("1234567");

        WebElement submit = driver.findElement(By.xpath("//*[@id='submit']"));
        submit.click();
        Thread.sleep(1000);

        WebElement addContactButton = driver.findElement(By.xpath("//button[@id ='add-contact']"));
        addContactButton.click();
        Thread.sleep(1000);

        WebElement AddContactList = driver.findElement(By.xpath("//h1[text()='Add Contact']"));
        Assert.assertEquals(AddContactList.getText(), "Add Contact");

    }
    @Test
    public void  TestAddContactError() throws  InterruptedException{
        driver.get("https://thinking-tester-contact-list.herokuapp.com");

        WebElement signUpButton = driver.findElement(By.xpath("//button[@id='signup']"));
        signUpButton.click();
        sleep(1000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInput.sendKeys("Ubergez");

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Odinson");

        WebElement eMail = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmail = generateUniqueEmail();
        eMail.sendKeys(uniqueEmail);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("1234567");

        WebElement submit = driver.findElement(By.xpath("//*[@id='submit']"));
        submit.click();
        sleep(1000);

        WebElement addContactButton = driver.findElement(By.xpath("//button[@id ='add-contact']"));
        addContactButton.click();
        Thread.sleep(1000);

        WebElement firstNameInputNewClient = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInputNewClient.sendKeys("Ubergez");

        WebElement lastNameNewClient = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastNameNewClient.sendKeys("Odinson");

        WebElement dateBirth = driver.findElement(By.xpath("//*[@id='birthdate']"));
        dateBirth.sendKeys("2000-12-12");

        WebElement eMailNewClient = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmailNewClient = generateUniqueEmail();
        eMailNewClient.sendKeys(uniqueEmailNewClient);

        WebElement phone = driver.findElement(By.xpath("//*[@id='phone']"));
        phone.sendKeys("456787654322");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='submit']"));
        submitButton.click();
        Thread.sleep(300);

        WebElement undefined = driver.findElement(By.xpath("//*[@id='error']"));
        Assert.assertEquals(undefined.getText(), "Contact validation failed: phone: Phone number is invalid");

    }
    @Test
    public void  TestAddContact() throws  InterruptedException{
        driver.get("https://thinking-tester-contact-list.herokuapp.com");

        WebElement signUpButton = driver.findElement(By.xpath("//button[@id='signup']"));
        signUpButton.click();
        sleep(1000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInput.sendKeys("Ubergez");

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Odinson");

        WebElement eMail = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmail = generateUniqueEmail();
        eMail.sendKeys(uniqueEmail);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("1234567");

        WebElement submit = driver.findElement(By.xpath("//*[@id='submit']"));
        submit.click();
        sleep(1000);

        WebElement addContactButton = driver.findElement(By.xpath("//button[@id ='add-contact']"));
        addContactButton.click();
        Thread.sleep(1000);

        WebElement firstNameInputNewClient = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInputNewClient.sendKeys("John");

        WebElement lastNameNewClient = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastNameNewClient.sendKeys("Dor");

        WebElement dateBirth = driver.findElement(By.xpath("//*[@id='birthdate']"));
        dateBirth.sendKeys("1990-12-14");

        WebElement eMailNewClient = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmailNewClient = generateUniqueEmail();
        eMailNewClient.sendKeys(uniqueEmailNewClient);

        WebElement phone = driver.findElement(By.xpath("//*[@id='phone']"));
        phone.sendKeys("8005555557");

        WebElement street1 = driver.findElement(By.xpath("//*[@id='street1']"));
        street1.sendKeys("1 Main St.");

        WebElement street2 = driver.findElement(By.xpath("//*[@id='street2']"));
        street2.sendKeys("Apartment B");

        WebElement city = driver.findElement(By.xpath("//*[@id='city']"));
        city.sendKeys("Oreo");

        WebElement stateProvince = driver.findElement(By.xpath("//*[@id='stateProvince']"));
        stateProvince.sendKeys("KS");

        WebElement postalCode = driver.findElement(By.xpath("//*[@id='postalCode']"));
        postalCode.sendKeys("12345");

        WebElement country = driver.findElement(By.xpath("//*[@id='country']"));
        country.sendKeys("USA");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='submit']"));
        submitButton.click();
        Thread.sleep(2000);

        WebElement ClickName = driver.findElement(By.cssSelector("tr.contactTableBodyRow"));
        ClickName.click();
        Thread.sleep(300);

        WebElement ContactDetails = driver.findElement(By.xpath("//h1[text()='Contact Details']"));
        Assert.assertEquals(ContactDetails.getText(), "Contact Details");

    }
    @Test
    public void testOpenBookStore() {
        driver.get("https://demoqa.com/");
        WebElement bookStore = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Book Store Application']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", bookStore);
        bookStore.click();
        String newUrl = driver.getCurrentUrl();

        Assert.assertEquals(newUrl, "https://demoqa.com/books");
    }
    @Test
    public void testCheckTestBox() {
        driver.get("https://demoqa.com/");
        WebElement element = driver.findElement(By.cssSelector(".avatar.mx-auto.white"));
        element.click();
        WebElement textBox = driver.findElement(By.xpath("//span[text()='Text Box']"));
        textBox.click();
        WebElement userName = driver.findElement(By.id("userName"));
        userName.sendKeys("Vasya Petrov");
        WebElement userEmail = driver.findElement(By.id("userEmail"));
        userEmail.sendKeys("Vasya@gmail.com");
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        currentAddress.sendKeys("Belarus, Minsk Pervomaiskaya str.71-15");
        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys("Belarus, Minsk Pervomaiskaya str.71-15");
        WebElement submit = driver.findElement(By.id("submit"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", submit);
        submit.click();
        WebElement output = driver.findElement(By.id("output"));
        String description = output.getText().trim().toLowerCase().replace("\n", "").replace("\\n", "");
        String expectedDescription = "name:vasya petrovemail:vasya@gmail.comcurrent address :belarus, minsk pervomaiskaya str.71-15permananet address :belarus, minsk pervomaiskaya str.71-15";
        Assert.assertEquals(description, expectedDescription);
    }
    @Test
    public void testDoubleClickCheck() throws InterruptedException{
        driver.get("https://demoqa.com/");
        WebElement element = driver.findElement(By.cssSelector(".avatar.mx-auto.white"));
        element.click();
        Thread.sleep(1000);
        WebElement buttons = driver.findElement(By.xpath("//span[text()='Buttons']"));
        buttons.click();
        WebElement doubleClick = driver.findElement(By.xpath("//button[text()='Double Click Me']"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClick).perform();
        WebElement doubleClickMessage = driver.findElement(By.xpath("//p[text()='You have done a double click']"));
        String actualResult = doubleClickMessage.getText().trim().toLowerCase();
        String expextedresult = "You have done a double click".trim().toLowerCase();
        Assert.assertEquals(actualResult, expextedresult);
    }
    @Test
    public void testRightClickCheck() throws InterruptedException{
        driver.get("https://demoqa.com/");
        WebElement element = driver.findElement(By.cssSelector(".avatar.mx-auto.white"));
        element.click();
        Thread.sleep(2000);
        WebElement buttons = driver.findElement(By.xpath("//span[text()='Buttons']"));
        buttons.click();
        WebElement rightClick = driver.findElement(By.xpath("//button[text()='Right Click Me']"));
        Actions actions = new Actions(driver);
        actions.contextClick(rightClick).perform();
        Thread.sleep(1000);
        WebElement rightClickMessage= driver.findElement(By.id("rightClickMessage"));
        String actualResult = rightClickMessage.getText().trim().toLowerCase();
        String expextedresult = "You have done a right click".trim().toLowerCase();
        Assert.assertEquals(actualResult, expextedresult);
    }

    @Test
    public void testBookSearchCountEmptyFieldWithFilter() throws InterruptedException {

        startTestDemoQaCom();
        openBookStoreApplication();
        Thread.sleep(500);

        WebElement serchBox = driver.findElement(By.xpath("//*[@class='form-control']"));
        serchBox.sendKeys("No Starch Press");
        Thread.sleep(150);

        List<WebElement> listOfBooks = driver.findElements(By.xpath("//*[@class = 'rt-tbody']//div[@class='rt-tr-group']"));
        Assert.assertEquals(listOfBooks.size(), 10);
        listOfBooks.removeIf(nextBook -> !nextBook.getText().equals("    "));
        Assert.assertEquals(listOfBooks.size(), 8);
    }

    @Test
    public void testCannotLoginWithoutLoginAndPassword() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        String error = driver.findElement(By.cssSelector("#rightPanel .title")).getText();
        String errorMessage = driver.findElement(By.cssSelector("#rightPanel .error")).getText();
        Assert.assertEquals(error, "Error!");
        Assert.assertEquals(errorMessage, "Please enter a username and password.");
    }
    @Test
    public void testCannotCreateNewAccountWithoutRequiredData() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.xpath("//a[contains(@href,'register.htm')]")).click();
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.firstName.errors']")).getText(), "First name is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.lastName.errors']")).getText(), "Last name is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.address.street.errors']")).getText(), "Address is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.address.city.errors']")).getText(), "City is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.address.state.errors']")).getText(), "State is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.address.zipCode.errors']")).getText(), "Zip Code is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.ssn.errors']")).getText(), "Social Security Number is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.username.errors']")).getText(), "Username is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='customer.password.errors']")).getText(), "Password is required.");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='repeatedPassword.errors']")).getText(), "Password confirmation is required.");
    }

    @Test
    public void testLeftMenuList() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        List <WebElement> leftMenuList = driver.findElements(By.xpath("//ul[@class='leftmenu']/li"));
        List <String> expectedleftMenuList = List.of("Solutions", "About Us", "Services", "Products", "Locations", "Admin Page");
        for (int i = 0; i < leftMenuList.size(); i++) {
            Assert.assertEquals(leftMenuList.get(i).getText(), expectedleftMenuList.get(i));
        }
    }


}