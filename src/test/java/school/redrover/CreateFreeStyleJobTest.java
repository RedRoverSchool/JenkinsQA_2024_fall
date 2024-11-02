package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CreateFreeStyleJobTest extends BaseTest {

    @Test
    public void testCreateJobForFreestyleProject() throws InterruptedException {
        String jobName = String.format("Job %s", randomString(5));
        String jobDescription = String.format("Description %s", randomString(15));
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        Thread.sleep(1000);

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(jobName);
        getDriver().findElement(By.cssSelector("li.hudson_model_FreeStyleProject")).click();
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(jobDescription);

        List<WebElement> checkBoxes = getDriver().findElements(By
                .xpath("//section[@nameref='rowSetStart28']//span[@class='jenkins-checkbox']/label[@class='attach-previous ']"));
        for (WebElement checkBox : checkBoxes) {
            checkBox.click();
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript("scrollBy(0,200)");
        }
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Thread.sleep(2000);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1.page-headline")).getText(), jobName);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("div#description")).getText(), jobDescription);
    }

    private String randomString(int n) {
        var rnd = new Random();
        Supplier<Integer> randomNumbers = () -> rnd.nextInt(26);
        return Stream.generate(randomNumbers)
                .limit(n)
                .map(i -> 'a' + i)
                .map((Character::toString))
                .collect(Collectors.joining());
    }
}
