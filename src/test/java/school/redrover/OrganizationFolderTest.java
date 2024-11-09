package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolderTest extends BaseTest {
    private static final String ITEM_NAME = "Item Name";
    private static String DISPLAYNAME = "Display Name";
    private static String DESCRIPTION = "Description";

    @Test
    public void testTitle() {
        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        String title = getDriver().findElement(By.id("general")).getText();

        Assert.assertEquals(title, "General");
    }

    @Test
    public void testTooltipGeneralEnabled() {
        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//span/label[@for='enable-disable-project']"))).perform();
        String tooltip = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__controls']/span")).getAttribute("tooltip");
        Assert.assertEquals(tooltip, "(No new builds within this Organization Folder will be executed until it is re-enabled)");
    }

    @Test
    public void testDescriptionPreview() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAYNAME);
        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//div/a[@class='textarea-show-preview']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getText(), DESCRIPTION);
    }

    @Test
    public void testDescriptionPreviewHide() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAYNAME);
        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//div/a[@class='textarea-show-preview']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getText(), DESCRIPTION);

        getDriver().findElement(By.xpath("//div/a[@class='textarea-hide-preview']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getAttribute("style"), "display: none;");

    }

    @Test
    public void testTooltipDelete() {
        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button hetero-list-add']")).click();
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-dropdown__item '][2]")).click();
        actions.moveToElement(getDriver().findElement(By.xpath("//div/button[@title='Delete']"))).perform();
        String tooltipButton = getDriver().findElement(By.xpath("//div/button[@title='Delete']")).getAttribute("tooltip");
        Assert.assertEquals(tooltipButton, "Delete");
    }

    @Test
    public void testDisplayNameAfterCreation() {
        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAYNAME);
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div/h1")).getText(), DISPLAYNAME);
    }

    @Test
    public void testItemNameAfterCreation() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAYNAME);
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();
        String itemNameExpected = "Folder name: " + ITEM_NAME;
        getDriver().findElement(By.xpath("//div[2]/div[2]")).getText();

        String itemNameActual = getDriver().findElement(By.id("main-panel")).getText();
        int indexNameStart = itemNameActual.indexOf("\n");
        int indexNameEnd = itemNameActual.indexOf("\n", indexNameStart+1);
        String itemNameActualCut = itemNameActual.substring(indexNameStart+1, indexNameEnd);

        Assert.assertEquals(itemNameActualCut, itemNameExpected);
    }

    @Test
    public void testDescriptionAfterCreation() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), DESCRIPTION);
    }

    @Test
    public void testChangeItemName() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//td/a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//div[7]/span/a")).click();
        String newName = ITEM_NAME + " NEW";
        getDriver().findElement(By.xpath("//div/input[@checkdependson='newName']")).click();
        getDriver().findElement(By.xpath("//div/input[@checkdependson='newName']")).sendKeys(" NEW");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div/h1")).getText(), newName);

    }

    @Test
    public void testCreateOrganizationFolderWithDefaultIcon() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Organization_Folder");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='OrganizationFolder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        new Select(getDriver().findElement(By.xpath("(//select[contains(@class, 'dropdownList')])[2]")))
                .selectByVisibleText("Default Icon");

        JavascriptExecutor js2 = (JavascriptExecutor) getDriver();
        js2.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.name("Submit")).click();

        String organizationFolderCurrentIcon = getDriver().findElement(By.cssSelector("h1 > svg")).getAttribute("title");
        Assert.assertEquals(organizationFolderCurrentIcon, "Folder");
    }

}
