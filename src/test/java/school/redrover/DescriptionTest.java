package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class DescriptionTest extends BaseTest {

    private static final String DESCRIPTION_TEXT = "It's my workspace";
    private static final String NEW_TEXT = "Hello! ";

    @Test
    public void testAdd() {

        String textDescription = new HomePage(getDriver())
                .clickAddDescription()
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testAdd")
    public void testEditDescription() {

        String newText = new HomePage(getDriver())
                .clickAddDescription()
                .enterDescription(NEW_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(newText, NEW_TEXT + DESCRIPTION_TEXT);
    }
}
