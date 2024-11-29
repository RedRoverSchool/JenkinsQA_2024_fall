package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class DescriptionTest extends BaseTest {

    private static final String DESCRIPTION_TEXT = "It's my workspace";

    @Test
    public void testAdd() {

        String descText = new HomePage(getDriver())
                .crateDescription(DESCRIPTION_TEXT)
                .getDescriptionText();

        Assert.assertEquals(descText, DESCRIPTION_TEXT);
    }

    @Test
    public void testEditDescription() {

        String descriptionText = new HomePage(getDriver())
                .crateDescription(DESCRIPTION_TEXT)
                .crateDescription("123")
                .getDescriptionText();

        Assert.assertEquals(descriptionText, "123" + DESCRIPTION_TEXT);
    }
}
