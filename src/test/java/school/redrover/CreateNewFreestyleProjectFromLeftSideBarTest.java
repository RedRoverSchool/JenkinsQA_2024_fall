package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.Random;

public class CreateNewFreestyleProjectFromLeftSideBarTest extends BaseTest {

    @Test
    public void create() throws InterruptedException {
        final String generatedName = generateRandomString(7);

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).clear();
        getDriver().findElement(By.id("name")).sendKeys(generatedName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//img[@id='jenkins-head-icon']")).click();

        List<String>namesOfJobsPresentedInJenkins =
                getDriver().findElements(By.xpath("//div[@id='main-panel']//tbody//tr//td//a//span"))
                        .stream()
                        .map(e -> e.getText())
                        .toList();

        Assert.assertTrue(namesOfJobsPresentedInJenkins.contains(generatedName));
    }

    private String generateRandomString(int stringLength){
        final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(stringLength);
        Random random = new Random();
        for (int i = 0; i < stringLength; i++) {
            sb.append(AlphaNumericString.charAt(random.nextInt(AlphaNumericString.length())));
        }
        return sb.toString();
    }
}
