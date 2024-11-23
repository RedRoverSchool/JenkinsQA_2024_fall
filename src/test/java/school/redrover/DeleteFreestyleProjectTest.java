package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class DeleteFreestyleProjectTest extends BaseTest {

    @Test
    public void testDeleteFirstProject() {
        final String firstProject = "First";
        final String secondProject = "Second";

        new HomePage(getDriver()).createFreestyleProject(firstProject);
        new HomePage(getDriver()).createFreestyleProject(secondProject);

        new HomePage(getDriver()).openDropdownViaChevron(firstProject);
        getDriver().findElement(By.xpath("//button[normalize-space()='Delete Project']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//dialog/div/button[text() = 'Yes']"))).click();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//td/a/span"));
        List<String> projectList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(projectList.size(),1);
        Assert.assertEquals(projectList.get(0), secondProject);
    }
}
