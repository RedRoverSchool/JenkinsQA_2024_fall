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
import java.util.List;

public class ArtsiomASecondTest {

    private WebDriver driver;


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

    @BeforeMethod
    public void startTest () {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");
    }

    @Test
    public void testNumberOfCategories() {

        List<WebElement> categoriestPanel = driver.findElements(By.xpath("//*[@class = 'card mt-4 top-card']"));
        Assert.assertEquals(categoriestPanel.size(), 6 );
    }

    @Test
    public void testSelectableGridSelectingObjects() throws InterruptedException {

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

        openBookStoreApplication();
        Thread.sleep(1000);
        List<WebElement> listOfBooks = driver.findElements(By.xpath("//*[@class = 'rt-tbody']//div[@class='rt-tr-group']"));
        Assert.assertEquals(listOfBooks.size(), 10);
        listOfBooks.removeIf(nextBook -> nextBook.getText().equals("    "));
        Assert.assertEquals(listOfBooks.size(), 8);
    }

    @Test
    public void testBookStoreFiltration5BooksPerPage() throws InterruptedException {

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


    @AfterMethod
    public void exitFromTheTest(){
        driver.quit();
    }
}
