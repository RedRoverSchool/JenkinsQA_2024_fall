package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuItemsTest extends BaseTest {

    @Test
    public void testCreateJobMenuItems() {

        getDriver().findElement(By.linkText("Create a job")).click();

        List<String> listOfExpectedSubMenuItems = Arrays.asList(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder");

        List<WebElement> listOfDashboardSubMenuItems = getDriver().findElements(By.xpath("//span[@class='label']"));
        List<String> extractedTexts = listOfDashboardSubMenuItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        Assert.assertEquals(extractedTexts, listOfExpectedSubMenuItems);



    }
}

