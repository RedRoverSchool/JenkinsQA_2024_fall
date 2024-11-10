package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class AnExistingFolderChangeTest extends BaseTest {

    private void createNewFolder () {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestFolder");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[6]/span/a")).click();
    }

    @Test
    public void testNoChangesWarning () {
        createNewFolder();

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[3]/div")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[3]/div")).getText(),
                "The new name is the same as the current name.");
    }

    @Test
    public void testSavingWithEmptyName () {
        createNewFolder();
        getDriver().findElement(By.name("newName")).clear();

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[3]/div")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[3]/div")).getText(),
                "No name is specified");
    }

    @Test
    public void testRenameFromFoldersPage () {
        createNewFolder();
        getDriver().findElement(By.name("newName")).sendKeys("2");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), "TestFolder2");
    }
}
