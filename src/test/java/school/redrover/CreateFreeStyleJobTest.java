package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CreateFreeStyleJobTest extends BaseTest {

    @Ignore
    @Test
    public void testCreateJobForFreestyleProject() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        String jobName = String.format("Job %s", randomString(5));
        String jobDescription = String.format("Description %s", randomString(15));
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".jenkins-input"))).sendKeys(jobName);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.hudson_model_FreeStyleProject"))).click();

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

        Assert.assertEquals(wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1.page-headline"))).getText(), jobName);
        Assert.assertEquals(wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#description"))).getText(), jobDescription);
    }

    private String randomString(int n) {
        Random rnd = new Random();
        Supplier<Integer> randomNumbers = () -> rnd.nextInt(26);
        return Stream.generate(randomNumbers)
                .limit(n)
                .map(i -> 'a' + i)
                .map((Character::toString))
                .collect(Collectors.joining());
    }
}
