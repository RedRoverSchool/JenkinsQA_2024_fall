package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class OrganizationFolderTest extends BaseTest {
    private static final String ITEM_NAME = "Item Name";
    private static final String DISPLAY_NAME = "Display Name";
    private static final String DESCRIPTION = "Description";

    private void scrollPage() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Test
    public void testTitle() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();

        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        String title = getDriver().findElement(By.id("general")).getText();

        Assert.assertEquals(title, "General");
    }

    @Test
    public void testTooltipGeneralEnabled() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();

        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//span/label[@for='enable-disable-project']"))).perform();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__controls']/span")).getAttribute("tooltip"),
                "(No new builds within this Organization Folder will be executed until it is re-enabled)");
    }

    @Test
    public void testDescriptionPreview() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAY_NAME);
        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//div/a[@class='textarea-show-preview']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getText(),
                DESCRIPTION);
    }

    @Test
    public void testDescriptionPreviewHide() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAY_NAME);
        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(DESCRIPTION);

        getDriver().findElement(By.xpath("//div/a[@class='textarea-show-preview']")).click();
        getDriver().findElement(By.xpath("//div/a[@class='textarea-hide-preview']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getAttribute("style"),
                "display: none;");
    }

    @Test
    public void testTooltipDelete() {

        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button hetero-list-add']")).click();
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-dropdown__item '][2]")).click();
        actions.moveToElement(getDriver().findElement(By.xpath("//div/button[@title='Delete']"))).perform();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/button[@title='Delete']")).getAttribute("tooltip"),
                "Delete");
    }

    @Test
    public void testDisplayNameAfterCreation() {
        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAY_NAME);
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/h1")).getText(),
                DISPLAY_NAME);
    }

    @Test
    public void testItemNameAfterCreation() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAY_NAME);
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();
        String itemNameExpected = "Folder name: " + ITEM_NAME;
        getDriver().findElement(By.xpath("//div[2]/div[2]")).getText();

        String itemNameActual = getDriver().findElement(By.id("main-panel")).getText();
        int indexNameStart = itemNameActual.indexOf("\n");
        int indexNameEnd = itemNameActual.indexOf("\n", indexNameStart+1);
        String itemNameActualCut = itemNameActual.substring(indexNameStart+1, indexNameEnd);

        Assert.assertEquals(itemNameActualCut, itemNameExpected);
    }
    @Ignore
    @Test
    public void testDescriptionAfterCreation() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();

        WebDriverWait driverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(
                getDriver().findElement(By.id("view-message")).getText(),
                DESCRIPTION);
    }

    @Test
    public void testChangeItemName() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//td/a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//div[7]/span/a")).click();
        getDriver().findElement(By.xpath("//div/input[@checkdependson='newName']")).click();
        getDriver().findElement(By.xpath("//div/input[@checkdependson='newName']")).sendKeys(" NEW");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/h1")).getText(),
                ITEM_NAME + " NEW");
    }

    @Test
    public void testCreateOrganizationFolderWithDefaultIcon() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Organization_Folder");

        scrollPage();

        getDriver().findElement(By.cssSelector("[class$='OrganizationFolder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        new Select(getDriver().findElement(By.xpath("(//select[contains(@class, 'dropdownList')])[2]")))
                .selectByVisibleText("Default Icon");

        scrollPage();

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1 > svg")).getAttribute("title"), "Folder");
    }

    @Test
    public void testFolderDelete() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//tbody/tr/td/a/span[contains(text(),ITEM_NAME)]")).click();
        getDriver().findElement(By.xpath("//div[5]/span/a")).click();

        WebDriverWait driverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@ data-id='ok']"))).click();
        List<WebElement> existingInstancesList = getDriver().findElements(By.xpath("//table[@id='projectstatus']/tbody"));

        Assert.assertTrue(existingInstancesList.isEmpty());
    }
}
