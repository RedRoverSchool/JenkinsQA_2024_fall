package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject9Test extends BaseTest {

    @Test

    public void testFirst() {

        getDriver().findElement(By.xpath("/html/body/div/div/div/div/span/a")).click();

        WebElement writeName = getDriver().findElement(By.xpath("/html/body/div/div/div/form/div/div/input"));
        writeName.sendKeys("Hello");

        getDriver().findElement(By.xpath("/html/body/div/div/div/form/div/div/div/ul/li")).click();
        getDriver().findElement(By.xpath("/html/body/div/div/div/form/div/div/button")).click();

    }
}

