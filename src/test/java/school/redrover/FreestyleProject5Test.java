package school.redrover;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.support.ui.Select;




public class FreestyleProject5Test extends BaseTest {
    @Test
    public void testCreateFreestyleProjectValidName() {
        getDriver().findElement(By.xpath("//div[@id='tasks']//div[1]//span[1]//a[1]")).click();
        WebElement name = getDriver().findElement(By.xpath("//input[@id='name']"));
        name.click();
        name.sendKeys("First project");

        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("First project");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText(), "First project");
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

        String period = getDriver().findElement(By.cssSelector("select[value='minute']")).getText();
        Assert.assertEquals(period, "Minute");



    }
}
