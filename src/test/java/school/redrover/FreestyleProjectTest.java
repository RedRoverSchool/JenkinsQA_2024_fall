package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectTest extends BaseTest {

    public void createFreestyleProjectUtils(String nameFreestyleProject) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameFreestyleProject);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    public void testCreateFreestyleProjectWithValidName() throws InterruptedException {
        String nameProject = "My first freestyle project";

        createFreestyleProjectUtils(nameProject);
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", nameProject)))
                .getText(), nameProject);
    }

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-required"))
                .getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateFreestyleProjectWithDuplicateName() throws InterruptedException {
        String nameProject = "TestNameProject";

        createFreestyleProjectUtils(nameProject);
        Thread.sleep(2000);

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameProject);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-invalid"))
                .getText(), String.format("» A job already exists with the name ‘%s’", nameProject));
    }

    @Test
    public void testAddDescriptionForFreestyleProject() throws InterruptedException {
        String nameProject = "TestNameProject";
        String description = "Description freestyle project.";

        createFreestyleProjectUtils(nameProject);
        Thread.sleep(2000);

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", nameProject)))
                .click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver()
                .findElement(By.xpath("//textarea[@name='description']"))
                .sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@id='description']/div"))
                .getText(), description);
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testCreateFreestyleProjectWithUnsafeCharactersInName(String unsafeCharacter) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(unsafeCharacter);
        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-invalid"))
                .getText(), "» ‘" + unsafeCharacter + "’ is an unsafe character");
    }


    @Test
    public void testRenameFreestyleProjectViaSidePanel() throws InterruptedException {
        String oldNameProject = "My first freestyle project";
        String newNameProject = "My second freestyle project";

        createFreestyleProjectUtils(oldNameProject);
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", oldNameProject)))
                .click();
        getDriver()
                .findElement(By.xpath("//a[contains(@href, 'confirm-rename')]"))
                .click();

        WebElement itemName = getDriver()
                .findElement(By.xpath("//input[@name='newName']"));
        itemName.clear();
        itemName.sendKeys(newNameProject);

        getDriver()
                .findElement(By.xpath("//button[@name='Submit']"))
                .click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", newNameProject)))
                .getText(), newNameProject);
    }

    @Test
    public void testDeleteFreestyleProjectViaSidePanel() throws InterruptedException {
        String nameProject = "My second freestyle project";

        createFreestyleProjectUtils(nameProject);
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", nameProject)))
                .click();
        getDriver()
                .findElement(By.xpath("//a[contains(@data-url, 'doDelete')]"))
                .click();
        Thread.sleep(1000);

        getDriver()
                .findElement(By.xpath("//button[@data-id='ok']"))
                .click();

        Thread.sleep(2000);

        boolean isElementPresent = getDriver()
                .findElements(By.xpath(String.format("//span[text()='%s']", nameProject)))
                .isEmpty();

        Assert.assertTrue(isElementPresent);
    }

}