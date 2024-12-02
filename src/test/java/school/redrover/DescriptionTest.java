package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class DescriptionTest extends BaseTest {

    private static final String DESCRIPTION_TEXT = "It's my workspace";

    @Test
    public void testAdd() {

        String textDescription = new HomePage(getDriver())
                .clickAddDescription()
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION_TEXT);
    }

    @Test
    public void testEditDescription() {

        String descriptionText = new HomePage(getDriver())
                .createDescription(DESCRIPTION_TEXT)
                .createDescription("123")
                .getDescriptionText();

        Assert.assertEquals(descriptionText, "123" + DESCRIPTION_TEXT);
    }
}
