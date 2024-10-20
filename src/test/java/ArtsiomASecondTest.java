import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class ArtsiomASecondTest {

    WebDriver driver;

    public void openInteractionsPage(){
        WebElement sectionInteractions = driver.findElement(By.xpath("//*[@class = 'card mt-4 top-card']//h5[contains(text(), 'Interactions')]"));
        sectionInteractions.click();
    }

    public void switchToGridVisualisation(){
        WebElement buttonGridFormat = driver.findElement(By.xpath("//a[@ id = 'demo-tab-grid']"));
        buttonGridFormat.click();
    }

    @BeforeMethod
    public void startTest () {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://demoqa.com/");
    }

    @Test
    public void testNumberOfCategories() {

        List<WebElement> leftPanel = driver.findElements(By.xpath("//*[@class = 'card mt-4 top-card']"));
        Assert.assertEquals(leftPanel.size(), 6 );
    }

    @Test
    public void testSelectableGridSelectingObjects() throws InterruptedException {

        openInteractionsPage();

        WebElement sectionSelectable = driver.findElement(By.xpath("//li[@class = 'btn btn-light ']//span[text() = 'Selectable']"));
        sectionSelectable.click();
        switchToGridVisualisation();

        List<WebElement> gridPanel = driver.findElements(By.xpath("//div[@ id = 'gridContainer']//li"));
        Assert.assertEquals(gridPanel.size(), 9);
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

    @Test (invocationCount = 10)
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

    @AfterTest
    public void exitFromTheTest(){
        driver.quit();
    }
}
