package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class AddNewItems1Test extends BaseTest {

    @Test
    public void testCreateNewFreestyleProject(){

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[1]/span")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("Freestyle folder");

        WebElement folderRadioButton = getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']/ul/li[1]"));
        folderRadioButton.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]"));
        saveButton.click();

        getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']/li[1]/a")).click();
        List<WebElement> elements = getDriver().findElements(By.xpath("//tr[@class=' job-status-nobuilt']/td[3]/a/span"));

        ArrayList<String> jobsList = new ArrayList<>();
        for(WebElement element: elements){
            jobsList.add(element.getText());
        }

        Assert.assertTrue(jobsList.contains("Freestyle folder"));
    }
}
