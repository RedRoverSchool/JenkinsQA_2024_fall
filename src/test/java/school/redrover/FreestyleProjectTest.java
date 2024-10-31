package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectTest extends BaseTest {

    @Test
    public void testCreateFreestyleProjectWithValidName() throws InterruptedException {
        String nameProject = "My first freestyle project";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(nameProject);

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        getDriver().findElement(By.id("ok-button")).click();

        Thread.sleep(2000);

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//span[text()='My first freestyle project']"))
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

}

