package school.redrover;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;


public class FreestyleProject5Test extends BaseTest {

    private final String FREESTYLE_PROJECT = "First project";

    private final String DESCRIPTION_NAME = "Project description";
    @Test
    public void testCreateFreestyleProjectValidName() {

        String createProject = new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectFreestyleProject(FREESTYLE_PROJECT)
                .typeDescription(DESCRIPTION_NAME)
                .getProjectName();
        Assert.assertEquals(createProject, FREESTYLE_PROJECT);


    }

    @Test
    public void testCreateFreestyleProjectInvalidName() {
        getDriver().findElement(By.xpath("//div[@id='tasks']//div[1]//span[1]//a[1]")).click();
        WebElement name = getDriver().findElement(By.xpath("//input[@id='name']"));
        name.click();
        name.sendKeys(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");

        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText(), ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");

    }

    @Test
    public void testCreateFreestyleProjectEmptyName() {
        getDriver().findElement(By.xpath("//div[@id='tasks']//div[1]//span[1]//a[1]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();

        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id=\"itemname-required\"]"))
                .getText(), "» This field cannot be empty, please enter a valid name");

    }

    @Test
    public void testCreateFreestyleProjectCheckbox() {
        getDriver().findElement(By.xpath("//div[@id='tasks']//div[1]//span[1]//a[1]")).click();
        WebElement name = getDriver().findElement(By.xpath("//input[@id='name']"));
        name.sendKeys("Project 3");

        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//label[normalize-space()='Throttle builds']")).click();
        Select builds = new Select(getDriver().findElement(By.xpath("//select[@name='_.durationName']")));
        builds.selectByVisibleText("Minute");

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//div[5]//span[1]//a[1]")).click();

        var period = getDriver().findElement(By.xpath("//*[@name='_.durationName']")).getAttribute("value");
        Assert.assertEquals(period, "minute");

    }

}
