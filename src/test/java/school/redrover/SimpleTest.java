package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.chrome.ChromeOptions;

public class SimpleTest extends BaseTest {
    @BeforeMethod
            public void setOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
    }


    @Test
    public void testWelcome() {
        String welcomeStr = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();

        Assert.assertEquals(welcomeStr, "Welcome to Jenkins!");
    }
}
