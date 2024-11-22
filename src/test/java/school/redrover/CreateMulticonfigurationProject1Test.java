package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateMulticonfigurationProject1Test extends BaseTest {
    
    @Test
    public void testCreateMultiConfigurationProject() {

        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Akiko");
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href ='/']")).click();
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();    
        getDriver().findElement(By.id("name")).sendKeys("Akiko");
        String result = getDriver().findElement(By.id("itemname-invalid")).getText();
       
        Assert.assertEquals(result, "» A job already exists with the name ‘Akiko’");
    }
}
