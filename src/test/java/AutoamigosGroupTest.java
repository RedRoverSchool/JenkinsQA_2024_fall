import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

public class AutoamigosGroupTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }

//    @AfterMethod
//    public void tearDown(){
//        driver.quit();
//    }

    @Test
    public void testClickHomeLink() throws InterruptedException {

        driver.get("https://demoqa.com/links");
        driver.manage().window().maximize();

        String mainWindowHandle = driver.getWindowHandle();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000);");

        WebElement linksHome = driver.findElement(By.xpath("//*[@id='simpleLink']"));
        linksHome.click();

        sleep(2000);

        Set<String> allWindowsHandle = driver.getWindowHandles();
        for (String window: allWindowsHandle) {
            if (!window.equals(mainWindowHandle)){
                driver.switchTo().window(window);
            }
        }

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/");
    }

    @Test
    public void testElementsCard() {

        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        WebElement elements = driver.findElement(By.xpath("//h5[text()='Elements']/../../.."));
        elements.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/elements");
    }

    @Test
    public void testCheckBoxAngular() throws InterruptedException {

        driver.get("https://demoqa.com/checkbox");
        driver.manage().window().maximize();

        WebElement checkBoxMenu = driver.findElement(By.xpath("//span[text()='Check Box']/.."));
        checkBoxMenu.click();

        WebElement expandAllButton = driver.findElement(By.xpath("//button[@aria-label='Expand all']"));
        expandAllButton.click();

        //ищем элемент class='rct-checkbox' являющийся предшествующим sibling для элемента span[text()='Angular']
        WebElement angularCheckBox = driver.findElement(By.xpath("//span[text()='Angular']/preceding-sibling::span[@class='rct-checkbox']"));
        angularCheckBox.click();

        WebElement resultOfSelection = driver.findElement(By.xpath("//span[text()='angular']"));

        Assert.assertEquals(resultOfSelection.getText(), "angular");
    }

    @Test
    public void testYesRadioButton() throws InterruptedException {

        driver.get("https://demoqa.com/elements");
        driver.manage().window().maximize();
        sleep(3000);

        WebElement radioButtonMenu = driver.findElement(By.xpath("//span[text()='Radio Button']/.."));
        radioButtonMenu.click();

        sleep(1000);

        WebElement yesRadioButton = driver.findElement(By.xpath("//input[@id='yesRadio']/following-sibling::label"));
        yesRadioButton.click();

        WebElement message = driver.findElement(By.xpath("//p/span[@class='text-success']"));

        Assert.assertEquals(message.getText(), "Yes");

    }

    @Test(description = "Практика заполнение Text Box https://demoqa.com/text-box ")

    public void TextBoxTest() throws InterruptedException {

        driver.get("https://demoqa.com/text-box");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

        WebElement fullName = driver.findElement(By.xpath("//*[@id='userName']"));
        fullName.sendKeys("Max");

        WebElement email = driver.findElement(By.xpath("//*[@id='userEmail']"));
        email.sendKeys("max@mail.ru");

        WebElement currentAddress = driver.findElement(By.xpath("//*[@id='currentAddress']"));
        currentAddress.sendKeys("Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");

        WebElement permanentAddress = driver.findElement(By.xpath("//*[@id='permanentAddress']"));
        permanentAddress.sendKeys("Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");

        // Прокрутка к элементу, найденному по XPath
        WebElement element = driver.findElement(By.xpath("//*[@id='submit']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        sleep(500);

        WebElement buttonSubmit = driver.findElement(By.xpath("//*[@id='submit']"));
        buttonSubmit.click();

        sleep(500);

        WebElement fieldName = driver.findElement(By.xpath("//*[@id='name']"));
        String name = fieldName.getText();
        Assert.assertEquals(name, "Name:Max");

        WebElement fieldEmail = driver.findElement(By.xpath("//*[@id='email']"));
        String name1 = fieldEmail.getText();
        Assert.assertEquals(name1, "Email:max@mail.ru");

        WebElement fieldCurrentAddress = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[6]/div/p[3]"));
        String name2 = fieldCurrentAddress.getText();
        Assert.assertEquals(name2,"Current Address :Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");

        WebElement fieldPermanentAddress = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[6]/div/p[4]"));
        String name3 = fieldPermanentAddress.getText();
        Assert.assertEquals(name3, "Permananet Address :Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");
    }

    @Test(description = "Практика работы с radio button https://demoqa.com/radio-button")

    public void radioButtonTest() throws InterruptedException{

        driver.get("https://demoqa.com/radio-button");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        WebElement radioButtonYes = driver.findElement(By.xpath("//*[@id='app']/div/div/div/div[2]/div[2]/div[2]/label"));
        radioButtonYes.click();

        sleep(500);

        WebElement radioButtonImpressive = driver.findElement(By.xpath("//*[@id='app']/div/div/div/div[2]/div[2]/div[3]/label"));
        radioButtonImpressive.click();
    }

    @Test (description = "Практика работы с check box https://demoqa.com/checkbox")

    public void testCheckBox() throws InterruptedException {

        driver.get("https://demoqa.com/checkbox");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        WebElement toggleButtonHomeOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/span/button"));
        toggleButtonHomeOn.click();
        sleep(500);

        WebElement toggleButtonDesktopOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/span/button"));
        toggleButtonDesktopOn.click();
        sleep(500);

        WebElement checkBoxNotesOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[1]/span/label/span[1]"));
        checkBoxNotesOn.click();
        sleep(500);

        WebElement checkBoxCommandsOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[2]/span/label/span[1]"));
        checkBoxCommandsOn.click();
        sleep(500);

        WebElement checkBoxCommandsOff = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[2]/span/label/span[1]"));
        checkBoxCommandsOff.click();
        sleep(500);

        WebElement checkBoxNotesOff = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[1]/span/label/span[1]"));
        checkBoxNotesOff.click();
        sleep(500);

        WebElement toggleButtonHomeOff = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/span/button"));
        toggleButtonHomeOff.click();
        sleep(500);

        WebElement fieldCheckBoxHome = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/div/ol/li/span/label/span[1]"));
        Assert.assertTrue(fieldCheckBoxHome.isEnabled(), "Чек бокс должен быть доступным для нажатия");

        WebElement openAllFolders = driver.findElement(By.xpath("//*[@id='tree-node']/div/button[1]"));
        openAllFolders.click();
        sleep(500);

        WebElement closeAllFolders = driver.findElement(By.xpath("//*[@id='tree-node']/div/button[2]"));
        closeAllFolders.click();
        sleep(500);
    }

    @Test(description = "Практика работы с web tables https://demoqa.com/webtables")

    public void webTablesTest() throws InterruptedException{

        driver.get("https://demoqa.com/webtables");
        driver.manage().window().maximize();

        WebElement buttonAdd = driver.findElement(By.xpath("//*[@id='addNewRecordButton']"));
        buttonAdd.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userForm']")));

        WebElement firstName = modal.findElement(By.xpath("//*[@id='firstName']"));
        firstName.sendKeys("Max");

        WebElement lastName = modal.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Pankratov");

        WebElement email = modal.findElement(By.xpath("//*[@id='userEmail']"));
        email.sendKeys("max@mail.ru");

        WebElement age = modal.findElement(By.xpath("//*[@id='age']"));
        age.sendKeys("43");

        WebElement salary = modal.findElement(By.xpath("//*[@id='salary']"));
        salary.sendKeys("10000");

        WebElement department = modal.findElement(By.xpath("//*[@id='department']"));
        department.sendKeys("QA Automation");

        WebElement submit = modal.findElement(By.xpath("//*[@id='submit']"));
        submit.click();

        WebElement tables = driver.findElement(By.xpath("//*[@id='app']/div/div/div/div[2]"));
        tables.click();

        WebElement searchBox = driver.findElement(By.xpath("//*[@id='searchBox']"));
        searchBox.sendKeys("Max");

        sleep(500);

        WebElement tableEntry = driver.findElement(By.xpath("//*[contains(text(),'Max')]"));
        Assert.assertTrue(tableEntry.isDisplayed(), "Запись с именем Max не найдена в таблице");

        WebElement delete = driver.findElement(By.xpath("//*[@id='delete-record-4']"));
        sleep(500);
        delete.click();

        List<WebElement> deletedEntry = driver.findElements(By.xpath("//*[contains(text(),'Max')]"));
        Assert.assertTrue(deletedEntry.isEmpty(), "Запись с именем Max все еще присутствует в таблице после удаления");


    }

    @Test(description = "Практика нажатия click-click https://demoqa.com/buttons")

    public void buttonsClickTest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/buttons");
        driver.manage().window().maximize();

        WebElement buttonsDoubleClick = driver.findElement(By.xpath("//*[@id='doubleClickBtn']"));
        Actions actions1 = new Actions(driver);
        actions1.doubleClick(buttonsDoubleClick).perform();
        sleep(500);

        WebElement doubleClickMessage = driver.findElement(By.id("doubleClickMessage"));
        Assert.assertTrue(doubleClickMessage.isDisplayed(), "Сообщение о двойном клике не отображается");
        Assert.assertEquals(doubleClickMessage.getText(), "You have done a double click");

        WebElement buttonsRightClick = driver.findElement(By.xpath("//*[@id='rightClickBtn']"));
        Actions actions = new Actions(driver);
        actions.contextClick(buttonsRightClick).perform();
        sleep(500);

        WebElement rightClickMessage = driver.findElement(By.id("rightClickMessage"));
        Assert.assertTrue(rightClickMessage.isDisplayed(), "Сообщение о правом клике не отображается");
        Assert.assertEquals(rightClickMessage.getText(), "You have done a right click");

        WebElement buttonsClick = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/div[3]/button"));
        buttonsClick.click();
        sleep(500);

        WebElement clickMessage = driver.findElement(By.id("dynamicClickMessage"));
        Assert.assertTrue(clickMessage.isDisplayed(), "Сообщение о клике не отображается");
        Assert.assertEquals(clickMessage.getText(), "You have done a dynamic click");
    }

    @Test
    public void testCheckingListOfElementsMenu() throws InterruptedException {

        driver.get("https://demoqa.com/elements");
        driver.manage().window().maximize();

        sleep(3000);

        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='element-list collapse show']/ul/li"));

        List<String> actualNamesOfElements = new ArrayList<>();
        for (WebElement eachElement: elements) {
            actualNamesOfElements.add(eachElement.getText());
        }
//        System.out.println(actualNamesOfElements);

        List<String> expectedNamesOfElements = new ArrayList<>(List.of("Text Box", "Check Box", "Radio Button", "Web Tables", "Buttons", "Links", "Broken Links - Images", "Upload and Download", "Dynamic Properties"));
        Assert.assertEquals(expectedNamesOfElements, actualNamesOfElements);
    }

    @Test(description = "Практика переход на новое окно по ссылке https://demoqa.com/links")

    public void testClickNewWindows() throws InterruptedException {

        driver.get("https://demoqa.com/links");
        driver.manage().window().maximize();

        String mainWindowHandle = driver.getWindowHandle();
        driver.findElement(By.xpath("//*[@id='simpleLink']")).click();
        sleep(2000);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        String newWindowUrl = driver.getCurrentUrl();
        System.out.println("URL нового окна: " + newWindowUrl);
        String expectedUrl = "https://demoqa.com/";

        Assert.assertEquals(newWindowUrl, expectedUrl, "URL нового окна не соответствует ожидаемому!");
    }
}
