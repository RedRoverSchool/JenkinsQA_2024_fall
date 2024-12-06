package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FreestyleProject1Test extends BaseTest {

    private static final String NEW_FREESTYLE_PROJECT_NAME = "New freestyle project";
    private static final String DESCRIPTION = "Some description";

    @Test
    public void testFreestyleProjectDescriptionPreview() {
        new HomePage(getDriver())
                .createFreestyleProject(NEW_FREESTYLE_PROJECT_NAME);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        String preview = getDriver().findElement(By.className("textarea-preview")).getText();

        Assert.assertEquals(preview, DESCRIPTION);
    }

    @Test
    public void testJobNameSorting() {
        HomePage homePage = new HomePage(getDriver());

        List<String> projectNames = List.of("aaa", "bbb", "aabb");
        projectNames.forEach(homePage::createFreestyleProject);

        // This XPath targets the links containing the job names
        List<WebElement> jobLinks = getDriver()
                .findElements(By.xpath("//table[@id='projectstatus']//tbody//tr/td[3]/a"));

        // Extract text from the elements
        List<String> actualOrder = new ArrayList<>();
        for (WebElement link : jobLinks) {
            actualOrder.add(link.getText().trim());
        }

        // Create a copy of the list and sort it alphabetically for expected order
        List<String> expectedOrder = new ArrayList<>(actualOrder);
        Collections.sort(expectedOrder); // Ascending order

        // Verify if the actual order matches the expected order
        Assert.assertEquals(actualOrder, expectedOrder);
    }
}