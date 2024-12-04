package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class DescriptionTest extends BaseTest {

    private static final String DESCRIPTION_TEXT = "It's my workspace";
    private static final String NEW_TEXT = "Hello! ";
    private static final String TEXT_DESCRIPTION_BUTTON = "Add description";

    @Test
    public void testAdd() {

        String textDescription = new HomePage(getDriver())
                .clickDescriptionButton()
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testAdd")
    public void testEdit() {

        String newText = new HomePage(getDriver())
                .clickDescriptionButton()
                .enterDescription(NEW_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(newText, NEW_TEXT + DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testEdit")
    public void testDelete() {

        String descriptionButton = new HomePage(getDriver())
                .clickDescriptionButton()
                .clearDescription()
                .clickSaveButton()
                .getTextDescriptionButton();

        Assert.assertEquals(descriptionButton, TEXT_DESCRIPTION_BUTTON);
    }

    @Test
    public void testPreview() {

        String textPreview = new HomePage(getDriver())
                .clickDescriptionButton()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewButton()
                .getTextPreview();

        Assert.assertEquals(textPreview, DESCRIPTION_TEXT);
    }
}
